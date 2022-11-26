package by.bsuir.controller.business;

import by.bsuir.dto.auth.SignInResponseDto;
import by.bsuir.dto.auth.SignUpResponseDto;
import by.bsuir.entity.security.SecurityUserFirebase;
import by.bsuir.service.business.SecurityService;
import by.bsuir.service.business.SecurityUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/firebase")
@Slf4j
@RequiredArgsConstructor
public class SecurityUserController {

    private final SecurityUserService securityUserService;
    private final SecurityService securityService;

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUpFirebase(@RequestParam("email") String email,
                                                            @RequestParam("password") String password) {
        SignUpResponseDto signUpResponseDTO = securityUserService.signUp(email, password);
        return ResponseEntity.status(HttpStatus.OK).body(signUpResponseDTO);
    }

    @PostMapping("/sign_in")
    public ResponseEntity<SignInResponseDto> signIn(@RequestParam("email") String email,
                                                    @RequestParam("password") String password){
        SecurityUserFirebase user = securityService.getUser();
        SignInResponseDto signInResponseDTO = securityUserService.signIn(email, password);

        return ResponseEntity.status(HttpStatus.OK).body(signInResponseDTO);
    }
}
