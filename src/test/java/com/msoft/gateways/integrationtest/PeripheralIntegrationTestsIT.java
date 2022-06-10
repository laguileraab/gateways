package com.msoft.gateways.integrationtest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.msoft.gateways.dto.GenericResponseDTO;
import com.msoft.gateways.dto.PeripheralDTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class PeripheralIntegrationTestsIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    int id;
    Date createdAt;
    MockMvc mockMvc;

    PeripheralDTO peripheralDTO;

    @BeforeEach
    void setup() throws ParseException {
        peripheralDTO = PeripheralDTO.builder()
                .online(true)
                .vendor("Huawei")
                .build();

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        mockMvc = webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        MockMvcResponse response = RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(peripheralDTO)
                .when()
                .post("/api/v1/peripheral")
                .then()
                .log()
                .ifError()
                .statusCode(201)
                .contentType("application/json")
                .extract()
                .response();

        Object objectResponse = response
                .as(GenericResponseDTO.class)
                .getObjectResponse();
        id = (int) ((LinkedHashMap<String, Object>) objectResponse).get("id");
        createdAt = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSX")
                .parse((String) ((LinkedHashMap<String, Object>) objectResponse).get("createdAt"));
    }

    @AfterEach
    void clean() {

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/api/v1/peripheral/" + id)
                .then()
                .log()
                .ifError()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    void peripheralIntegrationTestIT() {
        peripheralDTO.setId(new Long(id));
        peripheralDTO.setVendor("Peripheral for test updated");
        peripheralDTO.setOnline(false);
        peripheralDTO.setCreatedAt(createdAt);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(peripheralDTO)
                .when()
                .put("/api/v1/peripheral/" + id)
                .then()
                .log()
                .ifError()
                .statusCode(200)
                .assertThat()
                .body(
                        "message",
                        equalTo("Peripheral updated successfully"),
                        "objectResponse.id",
                        equalTo(id),
                        "objectResponse.vendor",
                        equalTo("Peripheral for test updated"),
                        "objectResponse.online",
                        equalTo(false),
                        "statusCode",
                        equalTo(200));
    }
}
