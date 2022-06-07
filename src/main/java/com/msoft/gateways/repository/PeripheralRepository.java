package com.msoft.gateways.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msoft.gateways.model.Peripheral;

@Repository
public interface PeripheralRepository extends JpaRepository<Peripheral, Long>{
    
}
