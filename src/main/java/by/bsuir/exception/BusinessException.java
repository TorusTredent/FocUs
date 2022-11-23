package by.bsuir.exception;

import by.bsuir.exception.enums.ERROR_CODE;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class BusinessException extends RuntimeException{

    private ERROR_CODE error_code;
    private HttpStatus httpStatus = HttpStatus.OK;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public BusinessException(String message, ERROR_CODE error_code) {
        super(message);
        this.error_code = error_code;
    }

    public BusinessException(String message, ERROR_CODE error_code, HttpStatus httpStatus) {
        super(message);
        this.error_code = error_code;
        this.httpStatus = httpStatus;
    }
}
