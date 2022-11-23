package by.bsuir.service.business.impl;

import by.bsuir.dto.SignInResponseDto;
import by.bsuir.dto.SignUpResponseDto;
import by.bsuir.entity.User;
import by.bsuir.entity.enums.user.USER_STATUS;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.exception.SignInFailedException;
import by.bsuir.exception.SignUpFailedException;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.business.SecurityUserService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private UserService userService;
    @Autowired
    private SecurityService securityService;
    @Value("${FIREBASE_PROJECT_KEY}")
    private String projectKey;
    @Autowired
    private RestTemplate restTemplate;
    private static final String SIGN_UP_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key={key}";
    private static final String SIGN_IN_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key={key}";

    @Override
    public SignUpResponseDto signUp(String email, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("returnSecureToken", true);

        Map<String, Object> params = new HashMap<>();
        params.put("key", projectKey);

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<SignUpResponseDto> response;
        try {
            response = restTemplate.postForEntity(SIGN_UP_URL, mapHttpEntity, SignUpResponseDto.class, params);
        } catch (HttpClientErrorException ex) {
            throw new SignUpFailedException(ex.getResponseBodyAsString());
        }

        return response.getBody();
    }

    @Override
    public SignInResponseDto signIn(String email, String password) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> body = new HashMap<>();
        body.put("email", email);
        body.put("password", password);
        body.put("returnSecureToken", true);

        Map<String, Object> params = new HashMap<>();
        params.put("key", projectKey);

        HttpEntity<Map<String, Object>> mapHttpEntity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<SignInResponseDto> response;
        try {
            response = restTemplate.postForEntity(SIGN_IN_URL, mapHttpEntity,
                    SignInResponseDto.class, params);
        } catch (HttpClientErrorException ex) {
            throw new SignInFailedException(ex.getResponseBodyAsString());
        }
        return response.getBody();
    }

    @Override
    public boolean checkUserByContainsRegistrationToken() {
        User userDb = userService.findByFirebaseId(securityService.getUser().getUid());
        return Objects.nonNull(userDb.getFirebaseId());
    }

    @Override
    public boolean checkUserStatus(SecurityUserFirebase userFirebase) {
        String uId = userFirebase.getUid();
        User user = userService.findByFirebaseId(uId);
        return !user.getUser_status().equals(USER_STATUS.ACTIVE);
    }
}
