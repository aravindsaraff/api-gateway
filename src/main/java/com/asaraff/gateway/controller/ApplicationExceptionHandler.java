package com.asaraff.gateway.controller;

import com.asaraff.gateway.data.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request) {

        //log.error(ex.getLocalizedMessage(), ex);
        if(ex instanceof HttpClientErrorException) {
            return handleErrorResponse(((HttpClientErrorException)ex).getStatusCode(), new HttpHeaders());
        }
        return handleErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, new HttpHeaders());
    }


    private static ResponseEntity<Object> handleErrorResponse(HttpStatus status, HttpHeaders headers) {
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(
                 ResponseData.builder()
                        .status(status != null ? status.value() + "" : null)
                        .message("Exception")
                        .build(),
                headers,
                status);
    }
}
