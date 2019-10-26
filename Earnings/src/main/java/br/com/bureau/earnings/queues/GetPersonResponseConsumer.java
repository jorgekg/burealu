package br.com.bureau.earnings.queues;

import java.util.HashMap;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.PersonDTO;

@Component
public class GetPersonResponseConsumer {

	private static HashMap<Integer, PersonDTO> buffer;

	public GetPersonResponseConsumer() {
		if (GetPersonResponseConsumer.buffer == null) {
			GetPersonResponseConsumer.buffer = new HashMap<Integer, PersonDTO>();
		}
	}

	public void setBuffer(int id) {
		buffer.put(id, null);
	}

	public HashMap<Integer, PersonDTO> getBuffer() {
		return buffer;
	}

	@RabbitListener(queues = {"${queue.response.person}"})
	public void consumer(@Payload(required = false) PersonDTO person, @Payload Message message) throws Exception {
		String responseId = message.getMessageProperties().getHeaders().get("uuid").toString();
		if (!buffer.containsKey(Integer.parseInt(responseId))) {
			throw new Exception("Ignored message");
		}
		if (person != null && person.getId() != null) {
			buffer.put(Integer.parseInt(responseId), person);
		} else {
			buffer.remove(Integer.parseInt(responseId));
		}
	}
}
