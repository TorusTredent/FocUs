package by.bsuir.repository;


import by.bsuir.entity.Category;
import by.bsuir.entity.Pack;
import by.bsuir.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByFirebaseId(String firebaseId);
    boolean existsByIdAndCategory(Long userId, Category category);
    boolean existsByIdAndCustomPack(Long userId, Pack pack);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
