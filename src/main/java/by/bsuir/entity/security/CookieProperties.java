package by.bsuir.entity.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class CookieProperties {

    private String domain;
    private String path;
    private boolean httpOnly;
    private boolean secure;
    private int maxAgeInMinutes;
}