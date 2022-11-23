package by.bsuir.service.entity;

import by.bsuir.dto.SignInResponseDto;
import by.bsuir.dto.SignUpResponseDto;
import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;

import java.util.Optional;

public interface UserService {

    void checkUser(SecurityUserFirebase user);
    User findByFirebaseId(String uId);

    User save(User user);
}
