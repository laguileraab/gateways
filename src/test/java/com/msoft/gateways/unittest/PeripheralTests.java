package com.msoft.gateways.unittest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.msoft.gateways.dto.PeripheralDTO;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.service.PeripheralService;

@SpringBootTest
public class PeripheralTests {

    @Autowired
    PeripheralService peripheralService;

    PeripheralDTO peripheralDTO;
    Peripheral peripheral;
    int expectedCount;
    ModelMapper modelMapper = new ModelMapper();

    @BeforeEach
    void setup() {
        peripheral = Peripheral.builder()
                .online(true)
                .vendor("Peripheral for test")
                .build();

        expectedCount = peripheralService.findAllPeripherals().size();
        peripheralDTO = peripheralService.addPeripheral(peripheral);
        assertEquals(expectedCount + 1, peripheralService.findAllPeripherals().size());
    }

    @AfterEach
    void clean() {
        peripheralService.deletePeripheral(peripheralDTO.getId());
        assertEquals(expectedCount, peripheralService.findAllPeripherals().size());
    }

    @Test
    void peripheralTests() {
        Peripheral peripheralU = Peripheral.builder()
                .id(peripheralDTO.getId())
                .vendor("Peripheral for test updated")
                .online(false)
                .createdAt(peripheralDTO.getCreatedAt())
                .build();

        PeripheralDTO peripheralDTOU = peripheralService.updatePeripheral(peripheralU.getId(), peripheralU);
        assertNotEquals(peripheralDTO, peripheralDTOU);
    }
}
