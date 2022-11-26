package by.bsuir.dto.task;

import by.bsuir.entity.Category;
import by.bsuir.entity.enums.task.TASK_PRIORITY;
import by.bsuir.entity.enums.task.TASK_STATUS;
import by.bsuir.entity.enums.task.TASK_TYPE;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskDto {

    private Long id;
    private String name;
    private List<Category> categories;
    private LocalDateTime createDate;
    private LocalDateTime deadline;
    private TASK_STATUS taskStatus;
    private TASK_PRIORITY taskPriority;
}
