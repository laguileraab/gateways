package com.msoft.gateways.integrationtest;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.msoft.gateways.dto.GatewayDTO;
import com.msoft.gateways.dto.GenericResponseDTO;
import com.msoft.gateways.dto.PeripheralDTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.module.mockmvc.response.MockMvcResponse;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class GatewayIntegrationTestsIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    String id;
    int idPeripheral;
    MockMvc mockMvc;

    GatewayDTO gatewayDTO;
    Set<PeripheralDTO> peripherals = new HashSet<>();

    @BeforeEach
    void setup() {
        peripherals.add(
                PeripheralDTO.builder().online(true).vendor("Huawei").build());
        gatewayDTO = GatewayDTO
                .builder()
                .name("gateway for test")
                .ipAddress("127.127.127.127")
                .peripherals(peripherals)
                .build();

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
        mockMvc = webAppContextSetup(webApplicationContext).build();
        RestAssuredMockMvc.mockMvc(mockMvc);
        MockMvcResponse response = RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(gatewayDTO)
                .when()
                .post("/api/v1/gateway")
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
        id = (String) ((LinkedHashMap<String, Object>) objectResponse).get("id");
    }

    @AfterEach
    void clean() {
        
        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .when()
                .delete("/api/v1/gateway/" + id)
                .then()
                .log()
                .ifError()
                .statusCode(200)
                .contentType("application/json");
    }

    @Test
    void gatewayIntegrationTestIT() {
        gatewayDTO.setId(UUID.fromString(id));
        gatewayDTO.setName("gateway for test updated");
        gatewayDTO.setIpAddress("192.192.192.192");

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(gatewayDTO)
                .when()
                .put("/api/v1/gateway/" + id)
                .then()
                .log()
                .ifError()
                .statusCode(200)
                .assertThat()
                .body(
                        "message",
                        equalTo("Gateway updated successfully"),
                        "objectResponse.id",
                        equalTo(id),
                        "objectResponse.name",
                        equalTo("gateway for test updated"),
                        "objectResponse.ipAddress",
                        equalTo("192.192.192.192"),
                        "statusCode",
                        equalTo(200));
    }
}
