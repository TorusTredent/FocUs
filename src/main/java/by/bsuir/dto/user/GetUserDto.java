package by.bsuir.dto.user;

import by.bsuir.entity.enums.user.USER_ROLE;
import by.bsuir.entity.enums.user.USER_STATUS;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserDto {

    private String email;
    private String username;
    private String photoPath;
    private USER_STATUS user_status;
    private USER_ROLE user_role;
    private boolean friend;
}
