package by.bsuir.dto.user;

import by.bsuir.entity.enums.task.TASK_STATUS;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendTaskDto {

    private Long id;
    private String name;
    private TASK_STATUS status;
}
