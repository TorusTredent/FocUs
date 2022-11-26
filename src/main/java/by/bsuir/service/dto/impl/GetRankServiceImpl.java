package by.bsuir.service.dto.impl;

import by.bsuir.entity.Rank;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetRankService;
import by.bsuir.service.dto.GetTaskService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class GetRankServiceImpl implements GetRankService {

    private final SecurityService securityService;
    private final UserService userService;
    private final GetTaskService getTaskService;
    private final TaskService taskService;

    @Autowired
    public GetRankServiceImpl(SecurityService securityService, UserService userService, GetTaskService getTaskService, TaskService taskService) {
        this.securityService = securityService;
        this.userService = userService;
        this.getTaskService = getTaskService;
        this.taskService = taskService;
    }

    @Override
    public Rank getRank() {
        User user = userService.findByFirebaseId(getUid());

        List<Task> tasksBetweenDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), user.getEnjoyPackTime(), user.getEndPackTime());

        if (user.getUsedPack().getRanks().isEmpty()) {
            log.warn("Ranks in used pack not found");
            return null;
        }

        return getRankByTasks(tasksBetweenDate, user.getUsedPack().getRanks());
    }

    private Rank getRankByTasks(List<Task> tasksBetweenDate, List<Rank> ranks) {
        Long completed = getTaskService.getNumberOfCompletedTasks(tasksBetweenDate);
        return ranks.stream()
                .sorted(Comparator.comparing(Rank::getMaxCompletedTasks))
                .filter(rank -> rank.getMaxCompletedTasks() >= completed && rank.getMinCompletedTasks() <= completed)
                .findFirst().orElse(ranks.get(ranks.size() - 1));
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
