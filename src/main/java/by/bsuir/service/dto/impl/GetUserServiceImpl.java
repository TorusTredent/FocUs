package by.bsuir.service.dto.impl;

import by.bsuir.dto.user.UserProfileDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.exception.BusinessException;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetRankService;
import by.bsuir.service.dto.GetTaskService;
import by.bsuir.service.dto.GetUserService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class GetUserServiceImpl implements GetUserService {

    private final SecurityService securityService;
    private final UserService userService;
    private final TaskService taskService;
    private final GetTaskService getTaskService;
    private final GetRankService getRankService;

    @Autowired
    public GetUserServiceImpl(SecurityService securityService, UserService userService, TaskService taskService, GetTaskService getTaskService, GetRankService getRankService) {
        this.securityService = securityService;
        this.userService = userService;
        this.taskService = taskService;
        this.getTaskService = getTaskService;
        this.getRankService = getRankService;
    }

    @Override
    public UserProfileDto getProfile() {
        User user = userService.findByFirebaseId(getUid());

        List<Task> tasksByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), user.getEnjoyPackTime(), user.getEndPackTime());

        return UserProfileDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .rank(getRankService.getRank())
                .numberOfCompletedTask(getTaskService.getNumberOfCompletedTasks(tasksByDate))
                .build();
    }

    @Override
    public List<User> getUserFriendsByEmails(User user, List<String> friendsEmail) {
        return friendsEmail.stream()
                .map(email -> user.getFollowers().stream()
                                        .filter(friend -> friend.getEmail().equals(email))
                                        .findFirst()
                                        .orElseThrow(() -> new BusinessException(String.format("User with email %s not found", email), NOT_FOUND)))
                .toList();
    }


    private String getUid() {
        return securityService.getUser().getUid();
    }
}
