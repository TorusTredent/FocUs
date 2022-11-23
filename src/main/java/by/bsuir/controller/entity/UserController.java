package by.bsuir.controller.entity;

import by.bsuir.entity.User;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final SecurityService service;
    private final UserService userService;

    @Autowired
    public UserController(SecurityService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<String> get() {
        SecurityUserFirebase user = service.getUser();
        User byFirebaseId = userService.findByFirebaseId(user.getUid());
        return new ResponseEntity<>(byFirebaseId.getFactOfDay(), HttpStatus.OK);
    }
}
