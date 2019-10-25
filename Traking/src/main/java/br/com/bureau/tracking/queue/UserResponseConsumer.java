package br.com.bureau.tracking.queue;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.models.User;

@Component
public class UserResponseConsumer {
	
	@RabbitListener(queues = {"${queue.response.user}"})
	public void consumer(@Payload User user, @Header("id") String responseId) {
	    System.out.println("Message read from  " + user.getEmail() + " - id " + responseId);
	}
}
