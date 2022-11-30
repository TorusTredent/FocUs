package by.bsuir.service.dto.impl;

import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskForStatDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.dto.user.FriendDto;
import by.bsuir.dto.user.FriendTaskDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetTaskService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static by.bsuir.entity.enums.task.TASK_PRIVACY.PUBLIC;
import static by.bsuir.entity.enums.task.TASK_STATUS.COMPLETED;
import static by.bsuir.entity.enums.task.TASK_STATUS.REMOVED;
import static by.bsuir.utils.DateUtils.*;

@Service
public class GetTaskServiceImpl implements GetTaskService {

    private final SecurityService securityService;
    private final UserService userService;
    private final TaskService taskService;

    public GetTaskServiceImpl(SecurityService securityService, UserService userService, TaskService taskService) {
        this.securityService = securityService;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public TaskStatDto getTaskStatByWeek(LocalDateTime atStartOfDay) {
        User user = userService.findByFirebaseId(getUid());

        LocalDateTime startOfCurrentWeek = getStartOfCurrentWeek(atStartOfDay);
        LocalDateTime endOfWeek = getEndOfWeek(atStartOfDay);

        List<Task> taskByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), startOfCurrentWeek, endOfWeek);

        return mapTasksToTaskStat(taskByDate);
    }

    @Override
    public List<TaskDto> getAllInWeek(LocalDateTime date) {
        User user = userService.findByFirebaseId(getUid());

        LocalDateTime startOfCurrentWeek = getStartOfCurrentWeek(date);
        LocalDateTime endOfWeek = getEndOfWeek(date);

        List<Task> taskByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), startOfCurrentWeek, endOfWeek);

        return mapTasksToTaskDto(taskByDate);
    }

    @Override
    public Long getNumberOfCompletedTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getTaskStatus() == COMPLETED)
                .count();
    }

    @Override
    public List<Task> getFriendTasks(User friend, User author, LocalDateTime time) {
        return friend.getTasks().stream()
                .filter(task -> task.getFriends().stream()
                        .anyMatch(user -> user.equals(author)))
                .filter(task -> task.getDeadLine().getDayOfYear() == time.getDayOfYear() &&
                        task.getDeadLine().getYear() == time.getYear())
                .collect(Collectors.toList());
    }

    @Override
    public List<FriendTaskDto> mapToFriendsTaskDto(User friend, User author, LocalDateTime time) {
        List<Task> tasks = getFriendTasks(friend, author, time);
        return tasks.stream()
                .map(task -> FriendTaskDto.builder()
                        .id(task.getId())
                        .status(task.getTaskStatus())
                        .name(task.getName())
                        .build())
                .toList();
    }

    @Override
    public List<TaskDto> getAllInMonth(LocalDateTime date) {
        User user = userService.findByFirebaseId(getUid());

        LocalDateTime startOfCurrentMonth = getStartOfMonth(date);
        LocalDateTime endOfMonth = getEndOfMonth(date);

        List<Task> taskByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), startOfCurrentMonth, endOfMonth);

        return mapTasksToTaskDto(taskByDate);
    }

    @Override
    public List<TaskDto> getAllInYear(LocalDateTime date) {
        User user = userService.findByFirebaseId(getUid());

        LocalDateTime startOfCurrentWeek = getStartOfYear(date);
        LocalDateTime endOfMonth = getEndOfYear(date);

        List<Task> taskByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), startOfCurrentWeek, endOfMonth);

        return mapTasksToTaskDto(taskByDate);
    }

    private TaskStatDto mapTasksToTaskStat(List<Task> tasks) {
        TaskStatDto taskStatDto = TaskStatDto.builder()
                .create(0L)
                .deleted(0L)
                .completed(0L)
                .build();
        tasks.forEach(task -> {
            taskStatDto.setCreate(taskStatDto.getCreate() + 1);
            if (task.getTaskStatus() == COMPLETED) {
                taskStatDto.setCompleted(taskStatDto.getCompleted() + 1);
            }
            if (task.getTaskStatus() == REMOVED) {
                taskStatDto.setDeleted(taskStatDto.getDeleted() + 1);
            }
        });

        taskStatDto.setTaskForStatDtos(tasks.stream()
                .map(task -> TaskForStatDto.builder()
                        .creationDate(task.getCreateDate())
                        .deadline(task.getDeadLine())
                        .task_type(task.getTaskType())
                        .build())
                .toList()
        );
        return taskStatDto;
    }

    private List<TaskDto> mapTasksToTaskDto(List<Task> taskByDate) {
        return taskByDate.stream()
                .map(task -> TaskDto.builder()
                        .id(task.getId())
                        .name(task.getName())
                        .categories(task.getCategory())
                        .createDate(task.getCreateDate())
                        .deadline(task.getDeadLine())
                        .taskStatus(task.getTaskStatus())
                        .taskPriority(task.getTaskPriority())
                        .friendDtos(task.getFriends().stream()
                                .map(user -> FriendDto.builder()
                                        .username(user.getUsername())
                                        .email(user.getEmail())
                                        .build())
                                .toList())
                        .build())
                .toList();
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
