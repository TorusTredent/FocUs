package by.bsuir.dto.task;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TaskStatDto {

    private Long create;
    private Long completed;
    private Long deleted;
    private List<TaskForStatDto> taskForStatDtos;
}
