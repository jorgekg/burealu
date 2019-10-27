package br.com.bureau.details.queues;

import java.util.Date;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastBuyDTO;
import br.com.bureau.details.mappers.LastBuyMapper;
import br.com.bureau.details.models.Moviment;
import br.com.bureau.details.models.enuns.PaymentMethod;
import br.com.bureau.details.services.LastBuyService;
import br.com.bureau.details.services.MovimentService;

@Component
public class LastBuyConsumer {

	@Autowired
	private LastBuyService lastBuyService;

	@Autowired
	private MovimentService movimentService;

	@Autowired
	private LastBuyMapper lastBuyMapper;

	@RabbitListener(queues = { "${queue.last.buy.update}" })
	public void consumer(@Payload(required = false) LastBuyDTO lastBuy) throws Exception {
		if (lastBuy != null) {
			if (lastBuy.getMethods() != null && lastBuy.getMethods().contains(PaymentMethod.CREDIT_CARD)) {
				this.lastBuyService.createOrUpdate(this.lastBuyMapper.toModel(lastBuy));
			} else {
				this.movimentService
						.create(new Moviment(null, lastBuy.getPersonId(), new Date(), lastBuy.getDetails()));
			}
		}
	}

}
