package by.bsuir.dto.task;

import by.bsuir.entity.enums.task.TASK_TYPE;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TaskForStatDto {

    private LocalDateTime creationDate;
    private LocalDateTime deadline;
    private TASK_TYPE task_type;
}
