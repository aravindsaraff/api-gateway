package com.asaraff.gateway.web;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * This custom response error handler simply suppresses the exceptions thrown from Spring RestTemplate
 * and allows the application to handle errors.
 */
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    private final ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return errorHandler.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
    }
}