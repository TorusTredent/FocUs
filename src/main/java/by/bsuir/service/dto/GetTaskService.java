package by.bsuir.service.dto;

import by.bsuir.dto.task.TaskDto;
import by.bsuir.dto.task.TaskStatDto;
import by.bsuir.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface GetTaskService {

    TaskStatDto getTaskStatByWeek(LocalDateTime atStartOfDay);

    List<TaskDto> getAll(LocalDateTime dateDto);

    LocalDateTime  getEndOfWeek(LocalDateTime date);

    LocalDateTime getStartOfCurrentWeek(LocalDateTime date);

    Long getNumberOfCompletedTasks(List<Task> tasks);
}
