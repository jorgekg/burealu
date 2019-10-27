package br.com.bureau.details.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastSearchDTO;
import br.com.bureau.details.mappers.LastSearchMapper;
import br.com.bureau.details.services.LastSearchService;

@Component
public class LastSearchConsumer {
	
	@Autowired
	private LastSearchService lastSearchService;
	
	@Autowired
	private LastSearchMapper lastSearchMapper;
	
	@RabbitListener(queues = {"${queue.last.search.update}"})
	public void consumer(@Payload(required = false) LastSearchDTO lastSearch) throws Exception {
		if (lastSearch != null) {
			this.lastSearchService.createOrUpdate(this.lastSearchMapper.toModel(lastSearch));
		}
	}
	
}

