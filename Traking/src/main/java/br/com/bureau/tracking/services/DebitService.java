package br.com.bureau.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.tracking.exceptions.ObjectNotFoundException;
import br.com.bureau.tracking.models.Debit;
import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.repositories.DebitRepository;
import br.com.bureau.tracking.validators.CertificatedValidator;

@Service
public class DebitService {

	@Autowired
	private DebitRepository debitRepository;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private CertificatedValidator certificatedValidator;
	
	public Debit findByPerson(Integer id, Integer personId) {
		Person person = this.personService.find(personId);
		Debit debit = this.debitRepository.findByIdAndPerson(id, person);
		if (debit == null) {
			throw new ObjectNotFoundException("Debit " + id + " not found");
		}
		this.certificatedValidator.validate(debit);
		return debit;
	}
	
	public Page<Debit> findAll(Integer personId, Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Person person = this.personService.find(personId);
		Page<Debit> debitPage = this.debitRepository.findByPerson(person, pageRequest);
		debitPage.stream().forEach(debitItem -> this.certificatedValidator.validate(debitItem));
		return debitPage;
	}
	
	public Debit create(Integer personId, Debit debit) {
		debit.setPerson(this.personService.find(personId));
		return this.debitRepository.save(debit);
	}
	
	public Debit update(Integer id, Integer personId, Debit debit) {
		Debit debitFinded = this.findByPerson(id, personId);
		debitFinded.setPrice(debit.getPrice());
		debitFinded.setCompany(debit.getCompany());
		return this.debitRepository.save(debitFinded);
	}
	
	public void delete(Integer id, Integer personId) {
		Debit debit = this.findByPerson(id, personId);
		this.debitRepository.delete(debit);
	}
}
