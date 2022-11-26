package by.bsuir.dto.task;

import by.bsuir.entity.enums.task.TASK_PRIORITY;
import by.bsuir.entity.enums.task.TASK_PRIVACY;
import by.bsuir.entity.enums.task.TASK_STATUS;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateTaskDto {

    private String name;
    private List<String> categoryNames;
    private TASK_PRIORITY taskPriority;
    private List<String> friendsEmail;
    private TASK_PRIVACY task_privacy;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime create;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;
}
