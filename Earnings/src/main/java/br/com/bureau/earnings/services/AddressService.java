package br.com.bureau.earnings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.AddressDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.queues.AddressSender;

@Service
public class AddressService {
	
	@Autowired
	private AddressSender addressSender;
	
	public AddressDTO create(String cpf, AddressDTO addressDTO) {
		AddressDTO address = this.addressSender.sendSync(cpf, addressDTO);
		if (address == null || address.getId() == null) {
			throw new ObjectNotFoundException("Person with CPF " + cpf + " not found");
		}
		return address;
	}
	
	public AddressDTO update(String cpf, Integer id, AddressDTO addressDTO) {
		addressDTO.setId(id);
		AddressDTO address = this.addressSender.sendSync(cpf, addressDTO);
		if (address == null || address.getId() == null) {
			throw new ObjectNotFoundException("Person with CPF " + cpf + " not found");
		}
		return address;
	}
	
}
