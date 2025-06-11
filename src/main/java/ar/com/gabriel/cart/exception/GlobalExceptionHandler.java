package ar.com.gabriel.cart.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;



/**
 * GlobalExceptionHandler 
 * 
 *  @author Gabriel Gonzalez
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceBadRequestException.class)
    public ResponseEntity<ErrorDetails> resourceBadRequestHandling(ResourceBadRequestException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetails> resourceNotFoundExceptionHandling(ResourceNotFoundException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(ResourceForbiddenException.class)
    public ResponseEntity<ErrorDetails> resourceForbiddenHandling(ResourceForbiddenException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(ResourceUnauthorizedException.class)
    public ResponseEntity<ErrorDetails> resourceUnauthorizedHandling(ResourceUnauthorizedException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(ResourceValidationException.class)
    public ResponseEntity<ErrorDetails> resourceValidationExceptionHandling(ResourceValidationException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleValidationErrors(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> errors = new ArrayList<>();

        for (FieldError error : exception.getBindingResult().getFieldErrors()) {
            errors.add("Campo [ " + error.getField() + " ] - Error: " + error.getDefaultMessage());
        }
        for (ObjectError error : exception.getBindingResult().getGlobalErrors()) {
            errors.add("Objeto [ " + error.getObjectName() + " ] - Error: " + error.getDefaultMessage());
        }

        ErrorDetails errorDetails = new ErrorDetails(
            new Date(),
            String.join("; ", errors),
            HttpStatus.BAD_REQUEST.value(),
            HttpStatus.BAD_REQUEST,
            "BAD_REQUEST",
            request.getDescription(false),
            exception.getStackTrace()
        );

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> globalExceptionHandling(Exception exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request, "INTERNAL_SERVER_ERROR");
    }

    private ResponseEntity<ErrorDetails> buildErrorResponse(RuntimeException exception, HttpStatus status, WebRequest request) {
        return buildErrorResponse(exception, status, request,
            exception instanceof BaseCustomException ? ((BaseCustomException) exception).getErrorCode() : status.name());
    }

    private ResponseEntity<ErrorDetails> buildErrorResponse(Exception exception, HttpStatus status, WebRequest request, String errorCode) {
        ErrorDetails errorDetails = new ErrorDetails(
            new Date(),
            exception.getMessage(),
            status.value(),
            status,
            errorCode,
            request.getDescription(false),
            exception.getStackTrace()
        );
        return new ResponseEntity<>(errorDetails, status);
    }
}
