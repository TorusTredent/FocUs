package by.bsuir.entity.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SecurityUserFirebase {

    private String uid;
    private String name;
    private String email;
    private boolean isEmailVerified;
    private String issuer;
    private String picture;

}