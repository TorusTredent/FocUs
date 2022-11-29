package by.bsuir.service.dto;

import by.bsuir.dto.task.CreateTaskDto;
import by.bsuir.dto.task.UpdateTaskDto;
import by.bsuir.entity.enums.task.TASK_STATUS;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface EditTaskService {

    boolean create(CreateTaskDto createTaskDto);

    boolean deleteById(Long taskId);

    boolean updateTask(UpdateTaskDto updateTaskDto);

    @Modifying
    @Transactional
    boolean updateTaskStatus(Long taskId, TASK_STATUS taskStatus);
}
