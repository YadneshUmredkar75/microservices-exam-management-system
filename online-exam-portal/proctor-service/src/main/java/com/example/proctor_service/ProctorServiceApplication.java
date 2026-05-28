package com.example.proctor_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProctorServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(
				ProctorServiceApplication.class, args);
	}
}