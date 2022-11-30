package by.bsuir.dto.pack;

import by.bsuir.dto.pack.rank.CreateRankDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePackDto {

    private String name;
    private String photoPath;
    private int daysBeforeOverwriting;
    private String description;
    private List<CreateRankDto> createRankDtos;
}
