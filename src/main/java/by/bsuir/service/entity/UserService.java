package by.bsuir.service.entity;

import by.bsuir.entity.Category;
import by.bsuir.entity.Pack;
import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;

import java.util.List;

public interface UserService {

    void checkUser(SecurityUserFirebase user);
    User findByFirebaseId(String uId);

    User save(User user);

    List<User> findAll();
    
    boolean existsByUserIdAndCategory(Long userId, Category category);

    boolean existsByUserIdAndPack(Long id, Pack pack);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
