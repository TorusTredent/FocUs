package by.bsuir.service.business;

import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;

public interface RegisterUserService {

    boolean registerUser(String username, String token, String email);
}
