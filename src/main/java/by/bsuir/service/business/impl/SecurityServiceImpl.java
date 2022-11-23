package by.bsuir.service.business.impl;

import by.bsuir.entity.security.Credentials;
import by.bsuir.entity.security.SecurityProperties;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.SecurityService;
import by.bsuir.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private CookieUtils cookieUtils;

    @Autowired
    private SecurityProperties securityProps;

    public SecurityUserFirebase getUser() {
        SecurityUserFirebase securityUserFirebasePrincipal = null;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Object principal = securityContext.getAuthentication().getPrincipal();
        if (principal instanceof SecurityUserFirebase) {
            securityUserFirebasePrincipal = ((SecurityUserFirebase) principal);
        }
        return securityUserFirebasePrincipal;
    }

    public Credentials getCredentials() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return (Credentials) securityContext.getAuthentication().getCredentials();
    }

    public boolean isPublic() {
        return securityProps.getAllowedPublicApis().contains(httpServletRequest.getRequestURI());
    }

    public String getBearerToken(HttpServletRequest request) {
        String bearerToken = null;
        String authorization = request.getHeader("Authorization");
        if (StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {
            bearerToken = authorization.substring(7);
        }
        return bearerToken;
    }
}