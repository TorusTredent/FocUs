package by.bsuir.service.dto;

import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.dto.user.FriendTaskDto;
import by.bsuir.entity.Task;
import by.bsuir.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public interface GetTaskService {

    TaskStatDto getTaskStatByWeek(LocalDateTime atStartOfDay);

    List<TaskDto> getAllInWeek(LocalDateTime date);

    Long getNumberOfCompletedTasks(List<Task> tasks);

    List<Task> getFriendTasks(User friend, User author, LocalDateTime time);

    List<FriendTaskDto> mapToFriendsTaskDto(User friend, User author, LocalDateTime time);

    List<TaskDto> getAllInMonth(LocalDateTime date);

    List<TaskDto> getAllInYear(LocalDateTime date);
}
