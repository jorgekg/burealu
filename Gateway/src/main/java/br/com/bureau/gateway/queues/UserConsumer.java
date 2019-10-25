package br.com.bureau.gateway.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.security.JWTUtil;
import br.com.bureau.gateway.services.UserService;


@Component
public class UserConsumer {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private UserService userService;
	
	@RabbitListener(queues = {"${queue.get.user}"})
	public void consumer(@Header("token") String token, @Header("id") String requestId) {
	    System.out.println("Message read from get.user id " + requestId);
	    User user = null;
	    if (this.jwtUtil.isValidToken(token)) {
	    	String email = this.jwtUtil.getUsername(token);
	    	System.out.println(email);
	    	try {
	    		user = this.userService.findEmail(email);
	    	} catch (Exception e) {
	    		// ignore this errors
			}
	    }
	    System.out.println(user);
	}
	
}
