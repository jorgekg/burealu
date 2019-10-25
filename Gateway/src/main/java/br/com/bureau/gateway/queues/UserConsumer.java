package br.com.bureau.gateway.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class UserConsumer {
	
	@RabbitListener(queues = {"${queue.get.user}"})
	public void listen(@Payload String token) {
	    System.out.println("Message read from myQueue : " + token);
	    
	}
	
}
