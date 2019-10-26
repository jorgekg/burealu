package br.com.bureau.tracking.mappers;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.PersonDTO;
import br.com.bureau.tracking.models.Person;

@Component
public class PersonMapper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public PersonDTO toDTO(Person person) {
		return new PersonDTO(person.getId(), person.getCpf(), person.getName());
	}
	
	public Person toModel(PersonDTO personDTO) {
		Person person = new Person();
		person.setId(personDTO.getId());
		person.setName(personDTO.getName());
		person.setCpf(personDTO.getCpf());
		return person;
	}
	
}
