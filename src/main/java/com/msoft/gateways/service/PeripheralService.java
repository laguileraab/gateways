package com.msoft.gateways.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.msoft.gateways.dto.PeripheralDTO;
import com.msoft.gateways.exception.FoundElementException;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.repository.PeripheralRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class PeripheralService {

    private final PeripheralRepository peripheralRepository;

    ModelMapper modelMapper = new ModelMapper();

    public PeripheralDTO findPeripheralById(Long id) {
        return modelMapper.map(peripheralRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Peripheral " + id + " does not exist")),
                PeripheralDTO.class);
    }

    public List<PeripheralDTO> findAllPeripherals() {
        return peripheralRepository.findAll().stream()
                .map(peripheral -> modelMapper.map(peripheral,
                        PeripheralDTO.class))
                .collect(Collectors.toList());
    }

    public PeripheralDTO addPeripheral(Peripheral peripheral) {
        if (peripheral.getId() != null && peripheralRepository.findById(peripheral.getId()).isPresent())
            throw new FoundElementException("Peripheral already exists");
        return modelMapper.map(
                peripheralRepository.save(peripheral),
                PeripheralDTO.class);
    }

    public PeripheralDTO updatePeripheral(Long id, Peripheral peripheral) {
        findPeripheralById(id);
        return modelMapper.map(
                peripheralRepository.save(peripheral),
                PeripheralDTO.class);
    }

    public void deletePeripheral(Long id) {
        findPeripheralById(id);
        peripheralRepository.deleteById(id);
    }
}
