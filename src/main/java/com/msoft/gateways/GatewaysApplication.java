package com.msoft.gateways;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GatewaysApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewaysApplication.class, args);
	}

}
