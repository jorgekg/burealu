package br.com.bureau.gateway.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
	private RabbitTemplate rabbitTemplate;
	
	@RabbitListener(queues = {"${queue.user}"})
	public void consumer(@Header("token") String token, @Header("id") String requestId) {
	    System.out.println("Message read from get.user id " + requestId);
	    if (this.jwtUtil.isValidToken(token)) {
	    	String email = this.jwtUtil.getUsername(token);
	    	System.out.println(email);
	    	try {
	    		User user = this.userService.findEmail(email);
	    		this.rabbitTemplate.convertAndSend("br.com.bureau.response.user", user, params -> {
		    		params.getMessageProperties().getHeaders().put("id", requestId);
		    		return params;
		    	});
	    	} catch (Exception e) {
	    		this.rabbitTemplate.convertAndSend("br.com.bureau.response.user", "", params -> {
		    		params.getMessageProperties().getHeaders().put("id", requestId);
		    		return params;
		    	});
			}
	    }
	}
	
}
