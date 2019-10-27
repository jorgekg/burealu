package br.com.bureau.tracking.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.AddressDTO;
@Component
public class AddressResponseSender {
	
	@Value("${queue.response.address}")
	private String personResponseQueue;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendAddress(AddressDTO address, String id) {
 		this.rabbitTemplate.convertAndSend(this.personResponseQueue, address != null ? address : "", params -> {
    		params.getMessageProperties().getHeaders().put("uuid", id);
    		params.getMessageProperties().getHeaders().remove("__TypeId__");
    		return params;
    	});
	}
}
