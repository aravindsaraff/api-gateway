package com.asaraff.gateway.controller;


import com.asaraff.gateway.services.GatewayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/v1")
@Api(value = "Gateway TO Service API", description = "Gateway Service REST API")
@Slf4j
public class GatewayController {
    @Value("${spring.async.deferred.timeout}")
    private long deferredTimeout;

    @Autowired
    private GatewayService gatewayService;

    @ApiOperation(value = "Simple Test for GET")
    @RequestMapping(value = "/helloworld", method = RequestMethod.GET)
    public ResponseEntity<String> getHelloName() {

        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }



    private static HttpHeaders addHeaders() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.APPLICATION_JSON);
        return responseHeaders;
    }
}
