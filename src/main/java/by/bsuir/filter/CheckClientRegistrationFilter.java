package by.bsuir.filter;

import by.bsuir.entity.security.NoSecureUrl;
import by.bsuir.service.business.SecurityUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 5)
@Component
public class CheckClientRegistrationFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private NoSecureUrl noSecureUrl;

    private static final String ERROR_MESSAGE =  "App not registered";
    private static final String CONTENT_TYPE =  "application/json;charset=UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String s = ((HttpServletRequest) request).getRequestURL().toString();

        boolean exist = false;
        for (String url : noSecureUrl.getUrl()) {
            url = url.replace("/**", "");
            if (s.contains(url)) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            String registration_token = request.getParameter("registration_token");
            if ((Objects.isNull(registration_token) || registration_token.isBlank()) && !securityUserService.checkUserByContainsRegistrationToken()) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, Object> errorObject = new HashMap<>();
                int errorCode = 401;
                errorObject.put("message", ERROR_MESSAGE);
                errorObject.put("error", UNAUTHORIZED);
                errorObject.put("code", errorCode);
                errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.setStatus(errorCode);
                httpResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
            }else {
                chain.doFilter(request,response);
            }
        }else {
            chain.doFilter(request,response);
        }
    }

}
