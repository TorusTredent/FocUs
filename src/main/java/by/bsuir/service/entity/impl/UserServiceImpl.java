package by.bsuir.service.entity.impl;

import by.bsuir.entity.Category;
import by.bsuir.entity.Pack;
import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.exception.BusinessException;
import by.bsuir.repository.UserRepository;
import by.bsuir.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

import static by.bsuir.exception.enums.ERROR_CODE.USER_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void checkUser(SecurityUserFirebase user) {
        User userDB = userRepository.findByFirebaseId(user.getUid()).orElse(null);

        if (Objects.isNull(userDB)) {
            userDB = User.builder()
                    .firebaseId(user.getUid())
                    .email(user.getEmail())
                    .username(user.getName())
                    .build();
            userRepository.save(userDB);
            log.debug("User with Uid:" + userDB.getFirebaseId() + " added to db");
        } else {
            if (!userDB.getEmail().equals(user.getEmail())) {
                userDB.setEmail(user.getEmail());
                log.debug("User email updated");
            }
        }
    }

    @Override
    public User findByFirebaseId(String uId) {
        return userRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new BusinessException(String.format("User with uid %s not found", uId),
                        USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean existsByUserIdAndCategory(Long userId, Category category) {
        return userRepository.existsByIdAndCategory(userId, category);
    }

    @Override
    public boolean existsByUserIdAndPack(Long id, Pack pack) {
        return userRepository.existsByIdAndCustomPack(id, pack);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(String.format("User with email %s not found", email), USER_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
