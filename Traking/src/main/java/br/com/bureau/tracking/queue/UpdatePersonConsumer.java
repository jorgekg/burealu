package br.com.bureau.tracking.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.services.PersonService;

@Component
public class UpdatePersonConsumer {

	@Autowired
	private PersonService personService;

	@Autowired
	private GetPersonResponseSender personResponseSender;

	@RabbitListener(queues = { "${queue.person.update}" })
	public void consumer(@Payload Person person ,@Payload Message message) {
		String uuid = message.getMessageProperties().getHeaders().get("uuid").toString();
		if (person != null) {
			try {
				person = this.personService.updateBirth(person);
			} catch (Exception e) {
				// ignore this error
			}
		}
		this.personResponseSender.sendUser(person, uuid);
	}

}
