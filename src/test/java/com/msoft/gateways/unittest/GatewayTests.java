package com.msoft.gateways.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msoft.gateways.dto.GatewayDTO;
import com.msoft.gateways.model.Gateway;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.repository.PeripheralRepository;
import com.msoft.gateways.service.GatewayService;

@SpringBootTest
public class GatewayTests {

    @Autowired
    GatewayService gatewayService;

    @Autowired
    PeripheralRepository peripheralRepository;

    GatewayDTO gatewayDTO;
    Gateway gateway;
    int expectedCount;
    Set<Peripheral> peripherals = new HashSet<>();
    ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        peripherals.add(Peripheral.builder()
        .online(true)
        .vendor("Huawei")
        .build());
        gateway = Gateway.builder()
            .name("gateway for test")
            .ipAddress("127.127.127.127")
            .peripherals(peripherals)
                .build();
        expectedCount = gatewayService.findAllGateways().size();
        gatewayDTO = gatewayService.addGateway(gateway);
        assertEquals(expectedCount + 1, gatewayService.findAllGateways().size());
    }

    @AfterEach
    void clean() {
        gatewayService.deleteGateway(gatewayDTO.getId().toString());
        peripheralRepository.deleteAll(modelMapper.map(gatewayDTO, Gateway.class).getPeripherals());
        assertEquals(expectedCount, gatewayService.findAllGateways().size());
    }

    @Test
    void gatewayTests() {
        Gateway gatewayU = Gateway.builder()
        .id(gatewayDTO.getId())
        .name("gateway for test updated")
        .ipAddress("192.192.192.192")
        .peripherals(peripherals)
                .build();

        GatewayDTO gatewayDTOU = gatewayService.updateGateway(gatewayU.getId().toString(), gatewayU);
        assertNotEquals(gatewayDTOU, gatewayU);
        assertNotEquals(gatewayDTO, gatewayDTOU);
    }
}
