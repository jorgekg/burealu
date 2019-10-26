package br.com.bureau.tracking.queue;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.UserDTO;
import br.com.bureau.tracking.exceptions.TimeoutException;

@Component
public class GetUserSender {

	@Value("${queue.user}")
	private String userQueue;

	@Value("${rabbit.timeout}")
	private Integer timeout;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private GetUserResponseConsumer responseConsumer;

	public UserDTO getUserByTokenSync(String token) {
		Random random = new Random();
		int id = Math.abs(random.nextInt());
		this.rabbitTemplate.convertAndSend(this.userQueue, "", params -> {
			params.getMessageProperties().getHeaders().put("token", token);
			params.getMessageProperties().getHeaders().put("uuid", id);
			return params;
		});
		return this.awaitMessage(id);
	}

	private UserDTO awaitMessage(int id) {
		this.responseConsumer.setBuffer(id);
		for (int i = 0; i < this.timeout; i++) {
			if (this.responseConsumer.getBuffer().containsKey(id)
					&& this.responseConsumer.getBuffer().get(id) == null) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
				}
				if (i >= 90) {
					throw new TimeoutException();
				}
			} else {
				break;
			}
		}
		return this.responseConsumer.getBuffer().containsKey(id) && this.responseConsumer.getBuffer().get(id) != null
				? this.responseConsumer.getBuffer().get(id)
				: null;
	}
	
}
