package by.bsuir.service.entity;

import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;

public interface UserService {

    void checkUser(SecurityUserFirebase user);
    User findByFirebaseId(String uId);

    User save(User user);
}
