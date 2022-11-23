package by.bsuir.filter;

import by.bsuir.entity.security.Credentials;
import by.bsuir.entity.security.SecurityProperties;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.entity.UserService;
import by.bsuir.utils.CookieUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieUtils cookieUtils;

    private SecurityProperties securityProps;

    private static final String COOKIE_NAME = "session";

    @Autowired
    public void setSecurityProps(SecurityProperties securityProps) {
        this.securityProps = securityProps;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        verifyToken(request);
        filterChain.doFilter(request, response);
    }

    private void verifyToken(HttpServletRequest request) {
        String session = null;
        FirebaseToken decodedToken = null;
        Credentials.CredentialType type = null;
        boolean strictServerSessionEnabled = securityProps.getFirebaseProps().isEnableStrictServerSession();
        Cookie sessionCookie = cookieUtils.getCookie(COOKIE_NAME);
        String token = securityService.getBearerToken(request);
        try {
            if (sessionCookie != null) {
                session = sessionCookie.getValue();
                decodedToken = FirebaseAuth.getInstance().verifySessionCookie(session,
                        securityProps.getFirebaseProps().isEnableCheckSessionRevoked());
                type = Credentials.CredentialType.SESSION;
            } else if (!strictServerSessionEnabled) {
                if (token != null && !token.equalsIgnoreCase("undefined")) {
                    decodedToken = FirebaseAuth.getInstance().verifyIdToken(token);
                    type = Credentials.CredentialType.ID_TOKEN;
                }
            }
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
            log.error("Firebase Exception:: ", e.getLocalizedMessage());
        }
        SecurityUserFirebase securityUserFirebase = firebaseTokenToUserDto(decodedToken);
        if (securityUserFirebase != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(securityUserFirebase,
                    new Credentials(type, decodedToken, token, session), null);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            userService.checkUser(securityUserFirebase);
        }
    }

    private SecurityUserFirebase firebaseTokenToUserDto(FirebaseToken decodedToken) {
        SecurityUserFirebase securityUserFirebase = null;
        if (decodedToken != null) {
            securityUserFirebase = SecurityUserFirebase.builder()
                    .uid(decodedToken.getUid())
                    .name(decodedToken.getName())
                    .email(decodedToken.getEmail())
                    .picture(decodedToken.getPicture())
                    .issuer(decodedToken.getIssuer())
                    .isEmailVerified(decodedToken.isEmailVerified())
                    .build();
        }
        return securityUserFirebase;
    }

}