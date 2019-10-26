package br.com.bureau.gateway;

import java.util.ArrayList;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.models.enums.Role;
import br.com.bureau.gateway.services.UserService;

@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
@EnableRabbit
public class GatewayApplication implements CommandLineRunner {
	
	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(Role.DETAILS);
		roles.add(Role.EARNINGS);
		roles.add(Role.TRACKING);
		User user = new User(null, null, "admin@admin", "12345", roles);
		User userSaved = this.userService.create(user);
		this.userService.updateRoles(userSaved.getId(), roles);
	}

}
