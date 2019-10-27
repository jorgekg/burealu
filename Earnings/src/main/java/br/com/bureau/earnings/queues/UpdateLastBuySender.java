package br.com.bureau.earnings.queues;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.LastBuyDTO;

@Component
public class UpdateLastBuySender {
	
	@Value("${queue.last.buy.update}")
	private String lastBuyUpdateQueue;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendAsync(LastBuyDTO lastBuyDTO) {
 		this.rabbitTemplate.convertAndSend(this.lastBuyUpdateQueue, lastBuyDTO != null ? lastBuyDTO : "", params -> {
    		params.getMessageProperties().getHeaders().remove("__TypeId__");
    		return params;
    	});
	}
}
