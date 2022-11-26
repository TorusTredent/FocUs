package by.bsuir.dto.user;

import by.bsuir.entity.Rank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileDto {

    private String username;
    private String email;
    private String photoPath;
    private Rank rank;
    private Long numberOfCompletedTask;
}
