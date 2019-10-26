package br.com.bureau.tracking.queue;

import java.util.HashMap;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.UserDTO;

@Component
public class GetUserResponseConsumer {
	
	private static HashMap<Integer, UserDTO> buffer;
	
	public GetUserResponseConsumer() {
		if (GetUserResponseConsumer.buffer == null) {
			GetUserResponseConsumer.buffer = new HashMap<Integer, UserDTO>();
		}
	}
	
	public void setBuffer(int id) {
		buffer.put(id, null);
	}
	
	public HashMap<Integer, UserDTO> getBuffer() {
		return buffer;
	}
	
	@RabbitListener(queues = {"${queue.response.user}"})
	public void consumer(@Payload(required = false) UserDTO user, @Payload Message message) throws Exception {
		String responseId = message.getMessageProperties().getHeaders().get("uuid").toString();
		if (!buffer.containsKey(Integer.parseInt(responseId))) {
			throw new Exception("Ignore mesage");
		}
		if (user != null && user.getEmail() != null) {
		    buffer.put(Integer.parseInt(responseId), user);
		} else {
			buffer.remove(Integer.parseInt(responseId));
		}
	}
	
}
