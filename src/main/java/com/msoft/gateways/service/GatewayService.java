package com.msoft.gateways.service;

import com.msoft.gateways.dto.GatewayDTO;
import com.msoft.gateways.dto.PeripheralDTO;
import com.msoft.gateways.exception.AddressExistsException;
import com.msoft.gateways.exception.FoundElementException;
import com.msoft.gateways.exception.FullGatewayException;
import com.msoft.gateways.model.Gateway;
import com.msoft.gateways.model.Peripheral;
import com.msoft.gateways.repository.GatewayRepository;
import com.msoft.gateways.repository.PeripheralRepository;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
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
    public final PeripheralRepository peripheralRepository;

    ModelMapper modelMapper = new ModelMapper();

    public GatewayDTO findGatewayById(String id) {
        return modelMapper.map(
                gatewayRepository
                        .findById(UUID.fromString(id))
                        .orElseThrow(
                                () -> new NoSuchElementException("Gateway " + id + " does not exist")),
                GatewayDTO.class);
    }

    public List<GatewayDTO> findAllGateways() {
        return gatewayRepository
                .findAll()
                .stream()
                .map(gateway -> modelMapper.map(gateway, GatewayDTO.class))
                .collect(Collectors.toList());
    }

    public GatewayDTO addGateway(Gateway gateway) {
        if (gateway.getId() != null &&
                gatewayRepository.findById(gateway.getId()).isPresent())
            throw new FoundElementException("Gateway already exists");

        if (gateway.getIpAddress() != null &&
                gatewayRepository.existsByIpAddress(gateway.getIpAddress()))
            throw new AddressExistsException("Ip address already asigned");

        if (gateway.getPeripherals() != null && gateway.getPeripherals().size() > 10)
            throw new FullGatewayException(
                    "Gateway can't have more than 10 peripherals");

        return modelMapper.map(gatewayRepository.save(gateway), GatewayDTO.class);
    }

    public GatewayDTO addPeripheralToGateway(String id, Peripheral peripheral) {
        Gateway gateway = modelMapper.map(findGatewayById(id), Gateway.class);
        Set<Peripheral> peripherals = gateway.getPeripherals();

        if (peripherals.size() > 9)
            throw new FullGatewayException(
                    "Gateway can't have more than 10 peripherals");

        Set<Peripheral> peripheralsNew = new HashSet<>();
        peripherals.forEach(
                p -> peripheralsNew.add(peripheralRepository.findById(p.getId()).get()));
        peripheralsNew.add(peripheral);
        gateway.setPeripherals(peripheralsNew);
        return modelMapper.map(gatewayRepository.save(gateway), GatewayDTO.class);
    }

    public GatewayDTO updateGateway(String id, Gateway gateway) {
        findGatewayById(id);
        gateway.setId(UUID.fromString(id));
        return modelMapper.map(gatewayRepository.save(gateway), GatewayDTO.class);
    }

    public void removePeripheralFromGateway(String id, Long idPeripheral) {
        Gateway gateway = modelMapper.map(findGatewayById(id), Gateway.class);
        Peripheral peripheral = peripheralRepository.findById(idPeripheral)
                .orElseThrow(() -> new NoSuchElementException("Peripheral " + idPeripheral + " not found"));
        Set<Peripheral> peripherals = gateway.getPeripherals();

        Set<Peripheral> peripheralsNew = new HashSet<>();
        peripherals.stream()
                .forEach(p -> peripheralsNew
                        .add(peripheralRepository.findById(p.getId()).get()));
        if (peripheralsNew.contains(peripheral)) {
            peripheralsNew.remove(peripheral);
            gateway.setPeripherals(peripheralsNew);
            gatewayRepository.save(gateway);
        } else {
            throw new NoSuchElementException("Peripheral not found in gateway with id " + id);
        }
    }

    public void deleteGateway(String id) {
        findGatewayById(id);
        gatewayRepository.deleteById(UUID.fromString(id));
    }
}
