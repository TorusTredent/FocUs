package by.bsuir.dto.pack.rank;

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
