package br.com.bureau.tracking.queue;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.AddressDTO;
import br.com.bureau.tracking.mappers.AddressMapper;
import br.com.bureau.tracking.services.AddressService;

@Component
public class AddressConsumer {

	@Autowired
	private AddressService addressService;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private AddressResponseSender personResponseSender;

	@RabbitListener(queues = { "${queue.address}" })
	public void consumer(@Payload AddressDTO address, @Payload Message message) {
		if (address != null) {
			String cpf = message.getMessageProperties().getHeaders().get("cpf").toString();
			String uuid = message.getMessageProperties().getHeaders().get("uuid").toString();
			try {
				if (address.getId() == null) {
					address = this.addressMapper
							.toDTO(this.addressService.create(cpf, this.addressMapper.toModel(address)));
				} else {
					address = this.addressMapper
							.toDTO(this.addressService.update(address.getId(), cpf, this.addressMapper.toModel(address)));
				}
			} catch (Exception e) {
				address = new AddressDTO();
			}
			this.personResponseSender.sendAddress(address, uuid);
		}
	}

}
