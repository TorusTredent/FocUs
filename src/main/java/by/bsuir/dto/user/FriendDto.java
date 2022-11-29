package by.bsuir.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendDto {

    private String username;
    private String email;
    private String photoPath;
}
