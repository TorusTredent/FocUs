package by.bsuir.dto.task;

import by.bsuir.entity.enums.task.TASK_PRIORITY;
import by.bsuir.entity.enums.task.TASK_PRIVACY;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTaskDto {

    private Long taskId;
    private String name;
    private LocalDateTime deadline;
    private List<String> friendsEmails;
    private TASK_PRIVACY taskPrivacy;
    private TASK_PRIORITY taskPriority;
}
