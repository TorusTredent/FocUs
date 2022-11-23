package by.bsuir.controller.business;

import by.bsuir.dto.VerifyRequestDto;
import by.bsuir.service.business.EmailService;
import by.bsuir.service.business.RegisterUserService;
import by.bsuir.service.business.RequestDtoChecksumService;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/registration/email")
@Slf4j
public class EmailRegistrationController {

    @Autowired
    private RequestDtoChecksumService requestDtoChecksumService;

    @Autowired
    private RegisterUserService registerUserService;

    @GetMapping(value = "/verify", params = {"registration_token", "checksum", "email", "username"})
    public ModelAndView verifyLink(@RequestParam(name="username") String username,
                                   @RequestParam(name = "registration_token") String token,
                                   @RequestParam(name = "checksum") String checksum,
                                   @RequestParam(name = "email") String email) {

        boolean validate = requestDtoChecksumService.validate(token, email, checksum);

        if (validate) {
            Message message = Message.builder()
                    .putData("email", email)
                    .putData("verify", "true")
                    .setToken(token)
                    .build();
            try {
                FirebaseMessaging.getInstance().send(message);
            } catch (FirebaseMessagingException fme) {
                log.error("Notification not sent:" + fme.getLocalizedMessage() +
                        " code:" + fme.getMessagingErrorCode());
            }
        }
        registerUserService.registerUser(username, token, email);

        Map<String, Object> model = new HashMap<>();
        model.put("email", email);
        model.put("validate", validate);
        return new ModelAndView("emailVerify", model);
    }
}
