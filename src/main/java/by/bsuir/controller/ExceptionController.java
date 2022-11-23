package by.bsuir.controller;

import by.bsuir.exception.BusinessException;
import by.bsuir.exception.SignUpFailedException;
import by.bsuir.exception.SignInFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(SignUpFailedException.class)
    public ResponseEntity<Object> handleMethodRegistrationFailedException(SignUpFailedException ex){
        log.warn(String.format("Bad request by %s", ex.getMessage()));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SignInFailedException.class)
    public ResponseEntity<Object> handleMethodRegistrationFailedException(SignInFailedException ex){
        log.warn(String.format("Bad request by %s", ex.getMessage()));
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleMethodFamilyNotFoundException(BusinessException businessException){
        if (businessException.getError_code() == null && businessException.getHttpStatus() == HttpStatus.OK) {
            log.warn(BAD_REQUEST + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getMessage(), BAD_REQUEST);
        }

        if (businessException.getHttpStatus() == HttpStatus.OK) {
            log.warn(BAD_REQUEST + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getError_code().getMessage(), BAD_REQUEST);
        }

        if(businessException.getError_code() == null) {
            log.warn(businessException.getHttpStatus() + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getMessage(), businessException.getHttpStatus());
        }

        log.warn(businessException.getHttpStatus() + " by {}", businessException.getMessage());

        return new ResponseEntity<>(businessException.getError_code().getMessage(), businessException.getHttpStatus());
    }
}
