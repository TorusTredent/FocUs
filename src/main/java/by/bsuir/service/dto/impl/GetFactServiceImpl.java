package by.bsuir.service.dto.impl;

import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.CheckUserService;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.dto.GetFactService;
import by.bsuir.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetFactServiceImpl implements GetFactService {

    private final UserService userService;
    private final SecurityService securityService;
    private final CheckUserService checkUserService;

    @Autowired
    public GetFactServiceImpl(UserService userService, SecurityService securityService, CheckUserService checkUserService) {
        this.userService = userService;
        this.securityService = securityService;
        this.checkUserService = checkUserService;
    }

    public String getFact() {
        checkUserService.checkUserFact(getUser());
        User user = userService.findByFirebaseId(getUid());
        return user.getFactOfDay();
    }


    private SecurityUserFirebase getUser() {
        return securityService.getUser();
    }
    private String getUid() {
        return securityService.getUser().getUid();
    }
}
