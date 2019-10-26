package br.com.bureau.earnings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.queues.GetPersonSender;
import br.com.bureau.earnings.queues.UpdatePersonSender;

@Service
public class PersonService {
	
	@Autowired
	private GetPersonSender personSender;
	
	@Autowired
	private UpdatePersonSender updatePersonSender;
	
	public PersonDTO find(Integer id) {
		PersonDTO person = this.personSender.getPersonByTokenSync(id.toString());
		if (person == null) {
			throw new ObjectNotFoundException("Person " + id + " not found");
		}
		return person;
	}
	
	public PersonDTO update(Integer id, PersonDTO personDTO) {
		PersonDTO person = this.find(id);
		person.setBirth(personDTO.getBirth());
		return this.updatePersonSender.updatePersonSync(person);
	}
	
}
