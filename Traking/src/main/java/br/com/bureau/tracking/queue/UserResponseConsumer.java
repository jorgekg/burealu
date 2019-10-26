package br.com.bureau.tracking.queue;

import java.util.HashMap;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.UserDTO;

@Component
public class UserResponseConsumer {
	
	private static HashMap<Integer, UserDTO> buffer;
	
	public UserResponseConsumer() {
		if (UserResponseConsumer.buffer == null) {
			UserResponseConsumer.buffer = new HashMap<Integer, UserDTO>();
		}
	}
	
	public void setBuffer(int id) {
		buffer.put(id, null);
	}
	
	public HashMap<Integer, UserDTO> getBuffer() {
		return buffer;
	}
	
	@RabbitListener(queues = {"${queue.response.user}"})
	public void consumer(@Payload(required = false) UserDTO user, @Header("uuid") String responseId) {
		if (user != null && user.getEmail() != null) {
		    buffer.put(Integer.parseInt(responseId), user);
		} else {
			buffer.remove(Integer.parseInt(responseId));
		}
	}
}
