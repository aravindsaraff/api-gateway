package com.asaraff.gateway.data;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(value = JsonInclude.Include.NON_DEFAULT)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/*
 * Represents a basic REST response. Can be used for success or failure.
 */
public class ResponseData {
    private String status;
    private String message;
}
