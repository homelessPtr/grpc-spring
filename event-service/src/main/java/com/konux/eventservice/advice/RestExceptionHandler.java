package com.konux.eventservice.advice;

import com.konux.eventservice.controller.model.BaseResponse;
import com.konux.eventservice.exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    private final String ERROR_RESULT = "ERROR";

    @ExceptionHandler(NullPointerException.class)
    protected ResponseEntity<BaseResponse> handleError(NullPointerException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder()
                        .result(ERROR_RESULT)
                        .note("NullPointerException at " + e.getStackTrace()[0].toString())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<BaseResponse> handleError(Throwable e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse
                        .builder()
                        .result(ERROR_RESULT)
                        .note(e.getMessage())
                        .build());
    }

    @ExceptionHandler(RpcException.class)
    protected ResponseEntity<BaseResponse> handleError(RpcException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse
                        .builder()
                        .result(ERROR_RESULT)
                        .note(e.getMessage())
                        .build());
    }
}
