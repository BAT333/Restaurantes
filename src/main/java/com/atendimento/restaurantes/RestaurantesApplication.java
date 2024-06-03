package com.atendimento.restaurantes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RestaurantesApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestaurantesApplication.class, args);
	}

}
