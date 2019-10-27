package br.com.bureau.earnings.queues;

import java.util.Random;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.AddressDTO;
import br.com.bureau.earnings.exceptions.TimeoutException;

@Component
public class AddressSender {

	@Value("${queue.address}")
	private String addressQueue;

	@Value("${rabbit.timeout}")
	private Integer timeout;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private AddressResponseConsumer responseConsumer;

	public AddressDTO sendSync(AddressDTO addres) {
		Random random = new Random();
		int id = Math.abs(random.nextInt());
		this.rabbitTemplate.convertAndSend(this.addressQueue, addres == null ? "" : addres, params -> {
			params.getMessageProperties().getHeaders().put("uuid", id);
			return params;
		});
		return this.awaitMessage(id);
	}

	private AddressDTO awaitMessage(int id) {
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
