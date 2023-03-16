package com.huytd.basecacheredis.exception;

import com.huytd.basecacheredis.constant.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

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

    private ErrorRequestResponse buildRequestResponse(List<String> errCodes, String requestUrl) {
        log.error("Exception: " + errCodes.toString());
        ErrorRequestResponse response = new ErrorRequestResponse();
        response.setErrorCodes(errCodes);
        response.setUrl(requestUrl);
        return response;
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorRequestResponse handlerConstraintViolationException(MethodArgumentNotValidException ex,
                                                                 HttpServletRequest request) {
        return buildRequestResponse(Collections.singletonList(ex.getLocalizedMessage()), request.getRequestURL().toString());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorRequestResponse handlerException(HttpServletRequest request) {
        return buildRequestResponse(Collections.singletonList(ErrorCodes.SYSTEM_MAINTAINING), request.getRequestURL().toString());
    }
}
