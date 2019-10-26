package br.com.bureau.tracking.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.services.PersonService;

@Component
public class PersonConsumer {

	@Autowired
	private PersonService personService;

	@Autowired
	private PersonResponseSender personResponseSender;

	@RabbitListener(queues = { "${queue.person}" })
	public void consumer(@Payload Message message) {
		String personId = message.getMessageProperties().getHeaders().get("personId").toString();
		String uuid = message.getMessageProperties().getHeaders().get("uuid").toString();
		Person person = null;
		if (personId != null) {
			person = this.personService.find(Integer.parseInt(personId));
		}
		this.personResponseSender.sendUser(person, uuid);
	}

}
