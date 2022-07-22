package com.dps_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DpsAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(DpsAdminApplication.class, args);
		System.err.println("::DPS ADMIN Application started:::");
	}

}
