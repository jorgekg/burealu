package br.com.bureau.tracking.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.models.Person;

@Component
public class PersonResponseSender {
	
	@Value("${queue.response.person}")
	private String personResponseQueue;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendUser(Person person, String id) {
 		this.rabbitTemplate.convertAndSend(this.personResponseQueue, person, params -> {
    		params.getMessageProperties().getHeaders().put("uuid", id);
    		params.getMessageProperties().getHeaders().remove("__TypeId__");
    		return params;
    	});
	}
}
