package br.com.bureau.earnings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.AddressDTO;
import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.queues.AddressSender;
import br.com.bureau.earnings.queues.GetPersonSender;

@Service
public class AddressService {

	@Autowired
	private GetPersonSender personSender;
	
	@Autowired
	private AddressSender addressSender;
	
	public AddressDTO create(String cpf, AddressDTO addressDTO) {
		PersonDTO personDTO = this.personSender.getPersonByCPFSync(cpf);
		if (personDTO == null) {
			throw new ObjectNotFoundException("Person with CPF " + cpf + " not found");
		}
		return this.addressSender.sendSync(addressDTO);
	}
	
}
