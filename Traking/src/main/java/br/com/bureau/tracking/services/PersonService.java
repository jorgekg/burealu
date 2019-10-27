package br.com.bureau.tracking.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.tracking.exceptions.CpfExistsException;
import br.com.bureau.tracking.exceptions.ObjectNotFoundException;
import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.repositories.PersonRepository;
import br.com.bureau.tracking.validators.CertificatedValidator;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Autowired
	private CertificatedValidator certificatedValidator;

	@Autowired
	private LastSearchService lastSearchService;

	public Person find(Integer id) {
		Optional<Person> optional = this.personRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ObjectNotFoundException("Person " + id + " not found");
		}
		this.certificatedValidator.validate(optional.get());
		return optional.get();
	}

	public Person findByCPF(String cpf) {
		return this.findByCPF(cpf, false);
	}
	
	public Person findByCPF(String cpf, Boolean isUpdateLastSearch) {
		Person person = this.personRepository.findByCpf(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Cpf " + cpf + " not found");
		}
		this.certificatedValidator.validate(person);
		if (isUpdateLastSearch) {
			this.lastSearchService.updateLastSearch(person.getId(), "Search person information");
		}
		return person;
	}

	public Page<Person> findAll(Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Person> pagePerson = this.personRepository.findAll(pageRequest);
		pagePerson.stream().forEach(person -> {
			this.lastSearchService.updateLastSearch(person.getId(), "Search person information");
			this.certificatedValidator.validate(person);
		});
		return pagePerson;
	}

	public Person create(Person person) {
		try {
			this.findByCPF(person.getCpf());
			throw new CpfExistsException();
		} catch (ObjectNotFoundException e) {
			// ignore this error
		}
		return this.personRepository.save(person);
	}

	public Person update(String cpf, Person person) {
		Person personFinded = this.findByCPF(cpf);
		personFinded.setName(person.getName());
		return this.personRepository.save(personFinded);
	}

	public Person updateBirth(Person person) {
		Person personFinded = this.find(person.getId());
		personFinded.setBirth(person.getBirth());
		return this.personRepository.save(personFinded);
	}
}
