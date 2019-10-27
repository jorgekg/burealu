package br.com.bureau.details;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableRabbit
public class DetailsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetailsApplication.class, args);
	}

}
