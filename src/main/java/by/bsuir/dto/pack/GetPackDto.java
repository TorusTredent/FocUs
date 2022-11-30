package by.bsuir.dto.pack;

import by.bsuir.entity.Rank;
import by.bsuir.entity.enums.pack.PACK_TYPE;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetPackDto {

    private Long id;
    private String name;
    private String photoPath;
    private String description;
    private int daysBeforeOverwriting;
    private int countOfFollowers;
    private List<Rank> ranks;
    private PACK_TYPE pack_type;
    private String authorUsername;
    private String authorEmail;
    private boolean isUsed;
}
