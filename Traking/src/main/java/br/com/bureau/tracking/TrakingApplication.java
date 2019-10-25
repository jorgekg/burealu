package br.com.bureau.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class TrakingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrakingApplication.class, args);
	}

}
