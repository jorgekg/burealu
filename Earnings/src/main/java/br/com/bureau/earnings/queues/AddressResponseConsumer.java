package br.com.bureau.earnings.queues;

import java.util.HashMap;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.AddressDTO;

@Component
public class AddressResponseConsumer {

	private static HashMap<Integer, AddressDTO> buffer;

	public AddressResponseConsumer() {
		if (AddressResponseConsumer.buffer == null) {
			AddressResponseConsumer.buffer = new HashMap<Integer, AddressDTO>();
		}
	}

	public void setBuffer(int id) {
		buffer.put(id, null);
	}

	public HashMap<Integer, AddressDTO> getBuffer() {
		return buffer;
	}

	@RabbitListener(queues = {"${queue.response.address}"})
	public void consumer(@Payload(required = false) AddressDTO address, @Payload Message message) throws Exception {
		String responseId = message.getMessageProperties().getHeaders().get("uuid").toString();
		if (!buffer.containsKey(Integer.parseInt(responseId))) {
			throw new Exception("Ignored message");
		}
		if (address != null && address.getId() != null) {
			buffer.put(Integer.parseInt(responseId), address);
		} else {
			buffer.put(Integer.parseInt(responseId), new AddressDTO());
		}
	}
}
