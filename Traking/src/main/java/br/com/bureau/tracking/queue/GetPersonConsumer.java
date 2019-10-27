package br.com.bureau.tracking.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.services.PersonService;

@Component
public class GetPersonConsumer {

	@Autowired
	private PersonService personService;

	@Autowired
	private GetPersonResponseSender personResponseSender;

	@RabbitListener(queues = { "${queue.person}" })
	public void consumer(@Payload Message message) {
		if (message.getMessageProperties().getHeaders().containsKey("cpf")) {
			String cpf = message.getMessageProperties().getHeaders().get("cpf").toString();
			String uuid = message.getMessageProperties().getHeaders().get("uuid").toString();
			Person person = null;
			if (cpf != null) {
				try {
					person = this.personService.findByCPF(cpf);
				} catch (Exception e) {
					// ignore this error
				}
			}
			this.personResponseSender.sendUser(person, uuid);
		}
	}

}
