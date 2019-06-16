package com.asaraff.gateway.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Data
@Builder
public class APILogger<R, B> {

    public static final ObjectMapper mapper = new ObjectMapper();

    private String uri;
    private R request;
    private HttpHeaders headers;
    private HttpStatus responseStatusCode;
    private B responseBody;
    private String notes;

    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException jpe) {
            throw new RuntimeException(jpe);
        }
    }
}
