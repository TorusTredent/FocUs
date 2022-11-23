package by.bsuir.entity.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class FirebaseProperties {

    int sessionExpiryInDays;

    String databaseUrl;

    boolean enableStrictServerSession;

    boolean enableCheckSessionRevoked;

    boolean enableLogoutEverywhere;
}