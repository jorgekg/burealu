package br.com.bureau.gateway.queues;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.gateway.models.User;

@Component
public class GetUserSender {
	
	@Value("${queue.response.user}")
	private String userResponseQueue;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendUser(User user, String id) {
 		this.rabbitTemplate.convertAndSend(this.userResponseQueue, user == null ? "" : user, params -> {
    		params.getMessageProperties().getHeaders().put("uuid", id);
    		params.getMessageProperties().getHeaders().remove("__TypeId__");
    		return params;
    	});
	}
	
}
