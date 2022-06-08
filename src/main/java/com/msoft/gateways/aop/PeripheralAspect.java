package com.msoft.gateways.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.msoft.gateways.model.Peripheral;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Aspect
public class PeripheralAspect {
    @Pointcut("execution(* com.msoft.gateways.service.PeripheralService..addPeripheral*(..))")
    public void addPeripheral() {
    }

    @Pointcut("execution(* com.msoft.gateways.service.PeripheralService..updatePeripheral*(..))")
    public void updatePeripheral() {
    }

    @Pointcut("execution(* com.msoft.gateways.service.PeripheralService..deletePeripheral*(..))")
    public void deletePeripheral() {
    }

    @Before("addPeripheral()")
    public void beforeAddPeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Adding peripheral " + ((Peripheral) jp.getArgs()[0]).getVendor() + "...");
    }

    @After("addPeripheral()")
    public void afterAddPeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Peripheral " + ((Peripheral) jp.getArgs()[0]).getVendor() + " added");
    }

    @Before("updatePeripheral()")
    public void beforeUpdatePeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Updating peripheral " + ((Peripheral) jp.getArgs()[1]).getId() + "...");
    }

    @After("updatePeripheral()")
    public void afterUpdatePeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Peripheral " + ((Peripheral) jp.getArgs()[1]).getId() + " updated");
    }

    @Before("deletePeripheral()")
    public void beforeDeletePeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Deleting peripheral with id: " + jp.getArgs()[0] + "...");
    }

    @After("deletePeripheral()")
    public void afterDeletePeripheral(JoinPoint jp) {
        PeripheralAspect.log.info("Peripheral with id: " + jp.getArgs()[0] + " deleted");
    }
}
