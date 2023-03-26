package com.huytd.basecacheredis.exception;

import com.huytd.basecacheredis.constant.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class BizExceptionHandler {

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorRequestResponse handlerUserRegistrationException(UserRegistrationException ex,
                                                                 HttpServletRequest request) {
        return buildRequestResponse(ex.getErrCodes(), request.getRequestURL().toString());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorRequestResponse handlerBadRequestException(BadRequestException ex,
                                                           HttpServletRequest request) {
        return buildRequestResponse(ex.getErrCodes(), request.getRequestURL().toString());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorRequestResponse handlerHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex,
                                                                              HttpServletRequest request) {
        return buildRequestResponse(Collections.singletonList(ex.getMessage()), request.getRequestURL().toString());
    }

    private ErrorRequestResponse buildRequestResponse(List<String> errCodes, String requestUrl) {
        log.error("Exception: " + errCodes.toString());
        ErrorRequestResponse response = new ErrorRequestResponse();
        response.setErrorCodes(errCodes);
        response.setUrl(requestUrl);
        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Map<String, String> handlerConstraintViolationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRequestResponse handlerException(HttpServletRequest request) {
        return buildRequestResponse(Collections.singletonList(ErrorCodes.SYSTEM_MAINTAINING), request.getRequestURL().toString());
    }
}
