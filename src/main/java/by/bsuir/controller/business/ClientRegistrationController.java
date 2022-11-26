package by.bsuir.controller.business;

import by.bsuir.dto.auth.SignInResponseDto;
import by.bsuir.dto.auth.SignInUserDto;
import by.bsuir.dto.auth.SignUpResponseDto;
import by.bsuir.dto.VerifyRequestDto;
import by.bsuir.service.business.EmailService;
import by.bsuir.service.business.RegisterUserService;
import by.bsuir.service.business.SecurityUserService;
import by.bsuir.service.entity.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/user")
@Slf4j
public class ClientRegistrationController {

    private final RegisterUserService registerUserService;
    private final SecurityUserService securityUserService;
    private final EmailService emailService;
    private final UserService userService;

    @Autowired
    public ClientRegistrationController(RegisterUserService registerUserService, SecurityUserService securityUserService, EmailService emailService, UserService userService) {
        this.registerUserService = registerUserService;
        this.securityUserService = securityUserService;
        this.emailService = emailService;
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<HttpStatus> registerUser(@RequestParam(name = "username") String username,
                                               @RequestParam("email") String email,
                                               @RequestParam("password") String password) {
        SignUpResponseDto signUpResponseDto = securityUserService.signUp(email, password);
        emailService.send(VerifyRequestDto.builder()
                                        .registration_token(signUpResponseDto.getLocalId())
                                        .email(signUpResponseDto.getEmail())
                                        .username(username)
                                        .build());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestBody SignInUserDto signUser) {
        SignInResponseDto signInResponseDto = securityUserService.signIn(signUser.getEmail(), signUser.getPassword());
        return new ResponseEntity<>(signInResponseDto, HttpStatus.OK);
    }
}
