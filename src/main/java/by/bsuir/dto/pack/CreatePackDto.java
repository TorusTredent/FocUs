package by.bsuir.dto.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreatePackDto {

    private String name;
    private int daysBeforeOverwriting;
    private String description;
    private List<CreateRankDto> createRankDtos;
}
