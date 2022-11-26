package by.bsuir.service.dto.impl;

import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskForStatDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetTaskService;
import by.bsuir.service.entity.TaskService;
import by.bsuir.service.entity.UserService;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

import static by.bsuir.entity.enums.task.TASK_STATUS.COMPLETED;
import static by.bsuir.entity.enums.task.TASK_STATUS.REMOVED;

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
    public List<TaskDto> getAll(LocalDateTime atStartOfDay) {
        User user = userService.findByFirebaseId(getUid());

        LocalDateTime startOfCurrentWeek = getStartOfCurrentWeek(atStartOfDay);
        LocalDateTime endOfWeek = getEndOfWeek(atStartOfDay);

        List<Task> taskByDate = taskService.findTaskByUserIdAndDateBetween(user.getId(), startOfCurrentWeek, endOfWeek);

        return mapTasksToTaskDto(taskByDate);
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
                        .build())
                .toList();
    }

    @Override
    public LocalDateTime getStartOfCurrentWeek(LocalDateTime date) {
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        return date.with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
    }

    @Override
    public LocalDateTime getEndOfWeek(LocalDateTime date) {
        DayOfWeek firstDayOfWeek = WeekFields.of(Locale.getDefault()).getFirstDayOfWeek();
        DayOfWeek lastDayOfWeek = firstDayOfWeek.plus(6);
        return date.with(TemporalAdjusters.nextOrSame(lastDayOfWeek));
    }

    @Override
    public Long getNumberOfCompletedTasks(List<Task> tasks) {
        return tasks.stream()
                .filter(task -> task.getTaskStatus() == COMPLETED)
                .count();
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
                .map(task -> {
                    return TaskForStatDto.builder()
                            .creationDate(task.getCreateDate())
                            .deadline(task.getDeadLine())
                            .task_type(task.getTaskType())
                            .build();
                })
                .toList()
        );
        return taskStatDto;
    }

    private String getUid() {
        return securityService.getUser().getUid();
    }
}
