package by.bsuir.entity.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties("security")
@Data
@Component
public class SecurityProperties {

    private CookieProperties cookieProps;
    private FirebaseProperties firebaseProps;
    boolean allowCredentials;
    private List<String> allowedOrigins;
    private List<String> allowedHeaders;
    private List<String> exposedHeaders;
    private List<String> allowedMethods;
    private List<String> allowedPublicApis;
}