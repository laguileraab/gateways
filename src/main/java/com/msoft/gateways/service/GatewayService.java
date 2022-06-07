package com.msoft.gateways.service;

import com.msoft.gateways.dto.GatewayDTO;
import com.msoft.gateways.exception.FoundElementException;
import com.msoft.gateways.model.Gateway;
import com.msoft.gateways.repository.GatewayRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class GatewayService {

    public final GatewayRepository gatewayRepository;

    ModelMapper modelMapper = new ModelMapper();

    public GatewayDTO findGatewayById(String id) {
        return modelMapper.map(gatewayRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new NoSuchElementException("Gateway " + id + " does not exist")), GatewayDTO.class);
    }

    public List<GatewayDTO> findAllGateways() {
        return gatewayRepository.findAll().stream()
                .map(peripheral -> modelMapper.map(peripheral,
                        GatewayDTO.class))
                .collect(Collectors.toList());
    }

    public GatewayDTO addGateway(Gateway gateway) {
        if (gateway.getId() != null && gatewayRepository.findById(gateway.getId()).isPresent())
            throw new FoundElementException("Peripheral already exists");
        return modelMapper.map(
                gatewayRepository.save(gateway),
                GatewayDTO.class);
    }

    public GatewayDTO updateGateway(String id, Gateway gateway) {
        findGatewayById(id);
        return modelMapper.map(
                gatewayRepository.save(gateway),
                GatewayDTO.class);
    }

    public void deleteGateway(String id) {
        findGatewayById(id);
        gatewayRepository.deleteById(UUID.fromString(id));
    }
}
