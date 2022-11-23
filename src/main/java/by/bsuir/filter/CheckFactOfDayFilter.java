package by.bsuir.filter;

import by.bsuir.entity.security.NoSecureUrl;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.CheckUserService;
import by.bsuir.service.business.SecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 5)
@Component
public class CheckFactOfDayFilter implements Filter {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private CheckUserService checkUserService;

    @Autowired
    private NoSecureUrl noSecureUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityUserFirebase authorizationServiceUser = securityService.getUser();

        String url = ((HttpServletRequest) request).getRequestURL().toString();
        boolean exist = checkUrl(url);
       
        if (!exist) {
            checkUserService.checkUserFact(authorizationServiceUser);
        }
        chain.doFilter(request, response);
    }


    private boolean checkUrl(String url) {
        for (String u : noSecureUrl.getUrl()) {
            u = u.replace("/**", "");
            if (url.contains(u)) {
                return true;
            }
        }
        return false;
    }
}
