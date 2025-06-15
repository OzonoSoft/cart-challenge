package ar.com.ozonosoft.taller.exception;

public abstract class BaseCustomException extends RuntimeException {
    private final String errorCode;

    public BaseCustomException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}