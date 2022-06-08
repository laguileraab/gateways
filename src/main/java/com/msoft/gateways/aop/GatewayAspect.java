package com.msoft.gateways.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.msoft.gateways.model.Gateway;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
@Aspect
public class GatewayAspect {
    @Pointcut("execution(* com.msoft.gateways.service.GatewayService..addGateway*(..))")
    public void addGateway() {
    }

    @Pointcut("execution(* com.msoft.gateways.service.GatewayService..updateGateway*(..))")
    public void updateGateway() {
    }

    @Pointcut("execution(* com.msoft.gateways.service.GatewayService..deleteGateway*(..))")
    public void deleteGateway() {
    }

    @Before("addGateway()")
    public void beforeAddGateway(JoinPoint jp) {
        GatewayAspect.log.info("Adding gateway " + ((Gateway) jp.getArgs()[0]).getIpAddress() + "...");
    }

    @After("addGateway()")
    public void afterAddGateway(JoinPoint jp) {
        GatewayAspect.log.info("Gateway " + ((Gateway) jp.getArgs()[0]).getIpAddress() + " added");
    }

    @Before("updateGateway()")
    public void beforeUpdateGateway(JoinPoint jp) {
        GatewayAspect.log.info("Updating gateway " + ((Gateway) jp.getArgs()[1]).getId() + "...");
    }

    @After("updateGateway()")
    public void afterUpdateGateway(JoinPoint jp) {
        GatewayAspect.log.info("Gateway " + ((Gateway) jp.getArgs()[1]).getId() + " updated");
    }

    @Before("deleteGateway()")
    public void beforeDeleteGateway(JoinPoint jp) {
        GatewayAspect.log.info("Deleting gateway with id: " + jp.getArgs()[0] + "...");
    }

    @After("deleteGateway()")
    public void afterDeleteGateway(JoinPoint jp) {
        GatewayAspect.log.info("Gateway with id: " + jp.getArgs()[0] + " deleted");
    }
}
