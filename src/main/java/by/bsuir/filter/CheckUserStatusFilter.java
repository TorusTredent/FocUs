package by.bsuir.filter;

import by.bsuir.entity.security.NoSecureUrl;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.business.SecurityUserService;
import by.bsuir.service.entity.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 4)
@Component
public class CheckUserStatusFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private NoSecureUrl noSecureUrl;

    @Autowired
    private SecurityUserService securityUserService;

    private static final String ERROR_MESSAGE = "User deleted";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityUserFirebase authorizationServiceUser = securityService.getUser();

        String s = ((HttpServletRequest) request).getRequestURL().toString();
        boolean exist = false;
        for (String url : noSecureUrl.getUrl()) {
            url = url.replace("/**", "");
            if (s.contains(url)) {
                exist = true;
                break;
            }
        }
        if (!exist){
            if (securityUserService.checkUserStatus(authorizationServiceUser)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, Object> errorObject = new HashMap<>();
                int errorCode = 403;
                errorObject.put("message", ERROR_MESSAGE);
                errorObject.put("error", HttpStatus.UNAUTHORIZED);
                errorObject.put("code", errorCode);
                errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.setStatus(errorCode);
                httpResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
            } else {
                chain.doFilter(request, response);
            }
        }else {
            chain.doFilter(request, response);
        }
    }
}
