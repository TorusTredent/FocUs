package by.bsuir.dto.pack;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePackDto {

    private Long packId;
    private String name;
    private String description;
    private int daysBeforeOverwriting;
}
