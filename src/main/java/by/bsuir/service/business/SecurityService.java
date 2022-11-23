package by.bsuir.service.business;

import by.bsuir.entity.security.Credentials;
import by.bsuir.entity.security.SecurityUserFirebase;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    SecurityUserFirebase getUser();
    Credentials getCredentials();
    boolean isPublic();
    String getBearerToken(HttpServletRequest request);
}
