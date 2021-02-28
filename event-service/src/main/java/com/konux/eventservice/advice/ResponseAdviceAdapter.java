package com.konux.eventservice.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
//@ControllerAdvice
public class ResponseAdviceAdapter implements ResponseBodyAdvice<Object> {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public boolean supports(MethodParameter methodParameter,
                            Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object o,
                                  MethodParameter methodParameter,
                                  MediaType mediaType,
                                  Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest,
                                  ServerHttpResponse serverHttpResponse) {

        if (serverHttpRequest instanceof ServletServerHttpRequest &&
                serverHttpResponse instanceof ServletServerHttpResponse) {

            logResponse(
                    ((ServletServerHttpRequest) serverHttpRequest).getServletRequest(),
                    ((ServletServerHttpResponse) serverHttpResponse).getServletResponse(), o);
        }

        return o;
    }

    private void logResponse(HttpServletRequest request, HttpServletResponse response, Object result) {
        try {
            if (request.getRequestURI().contains("actuator")) {
                return;
            }
            if (result != null) {
                if (result instanceof InputStreamResource) {
                    log.info("method {}:{} complete with status={}.",
                            request.getMethod(),
                            request.getRequestURI(),
                            response.getStatus());
                } else {
                    log.info("method {}:{} complete with status={}. result {}",
                            request.getMethod(),
                            request.getRequestURI(),
                            response.getStatus(),
                            mapper.writeValueAsString(result));
                }
            } else {
                log.info("method {}:{} complete with status={}.",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus());
            }
        } catch (Exception e) {
            log.error("Error log response", e);
        }
    }

}
