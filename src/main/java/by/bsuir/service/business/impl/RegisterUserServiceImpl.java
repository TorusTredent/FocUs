package by.bsuir.service.business.impl;

import by.bsuir.entity.User;
import by.bsuir.service.business.RegisterUserService;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.entity.PackService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static by.bsuir.entity.enums.user.USER_ROLE.USER;
import static by.bsuir.entity.enums.user.USER_STATUS.ACTIVE;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private final UserService userService;
    private final SecurityService securityService;
    private final PackService packService;

    @Autowired
    public RegisterUserServiceImpl(UserService userService, SecurityService securityService, PackService packService) {
        this.userService = userService;
        this.securityService = securityService;
        this.packService = packService;
    }

    @Override
    public boolean registerUser(String username, String token, String email) {
        User user = User.builder()
                .username(username)
                .firebaseId(token)
                .email(email)
                .usedPack(packService.findPackWithDefaultType())
                .user_role(USER)
                .user_status(ACTIVE)
                .build();
        userService.save(user);
        return true;
    }


    private String getUser() {
        return securityService.getUser().getUid();
    }
}
