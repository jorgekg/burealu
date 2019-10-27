package br.com.bureau.details.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastBuyDTO;
import br.com.bureau.details.mappers.LastBuyMapper;
import br.com.bureau.details.services.LastBuyService;

@Component
public class LastBuyConsumer {
	
	@Autowired
	private LastBuyService lastBuyService;
	
	@Autowired
	private LastBuyMapper lastBuyMapper;
	
	@RabbitListener(queues = {"${queue.last.buy.update}"})
	public void consumer(@Payload(required = false) LastBuyDTO lastSearch) throws Exception {
		if (lastSearch != null) {
			this.lastBuyService.createOrUpdate(this.lastBuyMapper.toModel(lastSearch));
		}
	}
	
}

