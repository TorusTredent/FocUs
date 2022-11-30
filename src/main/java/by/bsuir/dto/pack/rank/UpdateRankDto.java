package by.bsuir.dto.pack.rank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRankDto {

    private Long id;
    private Long packId;
    private String name;
}
