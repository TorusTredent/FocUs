package by.bsuir.service.entity.impl;

import by.bsuir.entity.Task;
import by.bsuir.exception.BusinessException;
import by.bsuir.exception.enums.ERROR_CODE;
import by.bsuir.repository.TaskRepository;
import by.bsuir.service.entity.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static by.bsuir.exception.enums.ERROR_CODE.TASK_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTaskByUserIdAdnDateAfter(Long userId, LocalDateTime date) {
        return taskRepository.findAllByAuthorIdAndAndCreateDateIsAfter(userId, date)
                .orElseThrow(() -> new BusinessException(String.format("Tasks with userId %s and date %s not found",
                                                            userId, date), TASK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public List<Task> findTaskByUserIdAndDateBetween(Long id, LocalDateTime startOfCurrentWeek, LocalDateTime endOfWeek) {
        return taskRepository.getAllBetweenDates(id, startOfCurrentWeek, endOfWeek)
                .orElseThrow(() -> new BusinessException(String.format("Tasks with userId %s and date %s not found",
                        id, startOfCurrentWeek), TASK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public void deleteById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public Task findById(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(String.format("Task with id %s not found", taskId), TASK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public void delete(Task task) {
        taskRepository.delete(task);
    }
}
