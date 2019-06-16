package com.asaraff.gateway.integration;


import com.asaraff.gateway.GatewayServiceApplication;
import com.asaraff.gateway.data.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {GatewayServiceApplication.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class GatewayControllerTestIT {

    @Autowired
    private ObjectMapper mapper;

    @LocalServerPort
    protected int targetContainerPort;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = targetContainerPort;
        RestAssured.baseURI = "http://localhost";
        RestAssured.basePath = "/v1";
        // set custom jackson object mapper
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(ObjectMapperConfig.objectMapperConfig().jackson2ObjectMapperFactory(
                (aClass, s) -> mapper
        ));
    }



    /*@Test
    public void testGetStoriesIncorrectAuth_403() {

                given().
                        accept("application/json").
                        queryParam("authtoken", 1234).
                        when().
                        get("/story").
                        then().
                        statusCode(403);

    }*/

    @Test
    public void testGetStories_200() {
        ResponseData response =
        given().
                accept("application/json").
                when().
                get("/story").
                then().
                statusCode(200).
                contentType(ContentType.JSON).
                extract().
                as(ResponseData.class);

        assertNotNull(response);
    }
}
