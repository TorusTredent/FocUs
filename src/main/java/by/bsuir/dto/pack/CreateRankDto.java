package by.bsuir.dto.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateRankDto {

    private String name;
    private Long maxCompletedTasks;
    private Long minCompletedTasks;
}
