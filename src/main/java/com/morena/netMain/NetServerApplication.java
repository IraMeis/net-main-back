package com.morena.netMain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication()
public class NetServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetServerApplication.class, args);
	}

}
