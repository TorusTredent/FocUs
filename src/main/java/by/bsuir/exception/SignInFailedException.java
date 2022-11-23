package by.bsuir.exception;

public class SignInFailedException extends RuntimeException{
    public SignInFailedException(String message) {
        super(message);
    }

    public SignInFailedException() {
        super();
    }

    public SignInFailedException(String message, Throwable cause) {
        super(message, cause);
    }

    public SignInFailedException(Throwable cause) {
        super(cause);
    }

    protected SignInFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
