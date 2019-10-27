package br.com.bureau.tracking.queue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.LastSearchDTO;

@Component
public class UpdateLastSearchSender {
	
	@Value("${queue.last.search.update}")
	private String lastSearchUpdateQueue;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendAsync(LastSearchDTO lastSearchDTO) {
 		this.rabbitTemplate.convertAndSend(this.lastSearchUpdateQueue, lastSearchDTO != null ? lastSearchDTO : "", params -> {
    		params.getMessageProperties().getHeaders().remove("__TypeId__");
    		return params;
    	});
	}
}
