package by.bsuir.service.entity;

import by.bsuir.entity.Task;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskService {

    List<Task> findTaskByUserIdAndDateBetween(Long id, LocalDateTime startOfCurrentWeek, LocalDateTime endOfWeek);

    Task save(Task task);

    void deleteById(Long taskId);

    Task findById(Long taskId);

    void delete(Task task);
}
