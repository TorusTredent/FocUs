package by.bsuir.service.business.impl;

import by.bsuir.dto.VerifyRequestDto;
import by.bsuir.service.business.EmailService;
import by.bsuir.service.business.RequestDtoChecksumService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private RequestDtoChecksumService checksumService;
    @Value("${EMAIL_LINK_VERIFY_DOMAIN}")
    @Setter
    private String domain;
    @Value("${spring.mail.username}")
    @Setter
    private String username;


    @Override
    public boolean send(VerifyRequestDto verifyRequestDto) {
        String checksum = checksumService.getChecksum(verifyRequestDto.getRegistration_token(), verifyRequestDto.getEmail());

        String link = domain + "/registration/email/verify?" +
                "username=" + verifyRequestDto.getUsername() + "&" +
                "email=" + verifyRequestDto.getEmail() + "&" +
                "registration_token=" + verifyRequestDto.getRegistration_token() + "&" +
                "checksum=" + checksum;

        SimpleMailMessage message = setMessageFields(verifyRequestDto.getEmail(), link);

        emailSender.send(message);
        log.debug("Verify email send from:" + message.getFrom() + " " +
                "to: " + message.getTo() + " " +
                "link: " + link);
        return true;
    }

    private SimpleMailMessage setMessageFields(String email, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject("email verification");
        message.setText(String.format("\nFollow the link to confirm your email \n %s ", link));
        return message;
    }
}
