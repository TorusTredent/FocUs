package by.bsuir.service.business;

import by.bsuir.dto.VerifyRequestDto;

public interface EmailService {

    boolean send(VerifyRequestDto verifyRequestDto);
}
