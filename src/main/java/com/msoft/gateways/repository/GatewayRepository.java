package com.msoft.gateways.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msoft.gateways.model.Gateway;

@Repository
public interface GatewayRepository extends JpaRepository<Gateway, UUID>{
    Boolean existsByIpAddress(String ipAddress);
}
