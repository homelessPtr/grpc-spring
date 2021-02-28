package com.konux.eventservice.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;

@Slf4j
@ControllerAdvice
public class RequestAdviceAdapter extends RequestBodyAdviceAdapter {


    final
    HttpServletRequest httpServletRequest;

    private final ObjectMapper objectMapper;

    public RequestAdviceAdapter(HttpServletRequest httpServletRequest, ObjectMapper objectMapper) {
        this.httpServletRequest = httpServletRequest;
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage,
                                MethodParameter parameter, Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {

        logRequest(httpServletRequest, body);

        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        logRequest(httpServletRequest, body);
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }

    private void logRequest(HttpServletRequest httpServletRequest, Object body) {
        if (httpServletRequest.getRequestURI().contains("actuator")) {
            return;
        }
        try {
            if (body != null) {
                log.info("method {}:{} payload: {}",
                        httpServletRequest.getMethod(),
                        httpServletRequest.getRequestURI(), (body instanceof String) ? body.toString() : objectMapper.writeValueAsString(body));
            } else {
                log.info("method {}:{}",
                        httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
            }
        } catch (Exception e) {
            log.error("Error logging body ", e);
        }
    }

}
