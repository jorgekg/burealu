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
	
	@Autowired
	private UserSender userSender;
	
	@RabbitListener(queues = {"${queue.user}"})
	public void consumer(@Header("token") String tokenBearer, @Header("uuid") String requestId) {
	    System.out.println("Consumer message read from user id " + requestId);
	    String token = tokenBearer.replace("Bearer ", "");
	    User user = null;
	    if (this.jwtUtil.isValidToken(token)) {
	    	String email = this.jwtUtil.getUsername(token);
	    	try {
	    		user = this.userService.findEmail(email);
	    	} catch (Exception e) {
	    		// ignore errors
			}
	    }
	    this.userSender.sendUser(user, requestId);
	}
	
}
