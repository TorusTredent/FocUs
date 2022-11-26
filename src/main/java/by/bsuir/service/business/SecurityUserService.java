package by.bsuir.service.business;

import by.bsuir.dto.auth.SignInResponseDto;
import by.bsuir.dto.auth.SignUpResponseDto;
import by.bsuir.entity.security.SecurityUserFirebase;

public interface SecurityUserService {

    SignUpResponseDto signUp(String email, String password);

    SignInResponseDto signIn(String email, String password);

    boolean checkUserByContainsRegistrationToken();

    boolean checkUserStatus(SecurityUserFirebase userFirebase);
}
