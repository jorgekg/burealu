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
	private LastSearchService lastSearchService;

	@Autowired
	private CertificatedValidator certificatedValidator;

	public Debit findByPerson(Integer id, String cpf) {
		return this.findByPerson(id, cpf, false);
	}
	
	public Debit findByPerson(Integer id, String cpf, Boolean isUpdateLastSearch) {
		Person person = this.personService.findByCPF(cpf);
		Debit debit = this.debitRepository.findByIdAndPerson(id, person);
		if (debit == null) {
			throw new ObjectNotFoundException("Debit " + id + " not found");
		}
		this.certificatedValidator.validate(debit);
		if (isUpdateLastSearch) {
			this.lastSearchService.updateLastSearch(person.getId(), "Search list debit");
		}
		return debit;
	}

	public Page<Debit> findAll(String cpf, Integer page, Integer size) {
		Person person = this.personService.findByCPF(cpf);
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Debit> debitPage = this.debitRepository.findByPerson(person, pageRequest);
		debitPage.stream().forEach(debitItem -> {
			this.lastSearchService.updateLastSearch(person.getId(), "Search list debit");
			this.certificatedValidator.validate(debitItem);
		});
		return debitPage;
	}

	public Debit create(String cpf, Debit debit) {
		debit.setPerson(this.personService.findByCPF(cpf));
		return this.debitRepository.save(debit);
	}

	public Debit update(Integer id, String cpf, Debit debit) {
		Debit debitFinded = this.findByPerson(id, cpf);
		debitFinded.setPrice(debit.getPrice());
		debitFinded.setCompany(debit.getCompany());
		return this.debitRepository.save(debitFinded);
	}

	public void delete(Integer id, String cpf) {
		Debit debit = this.findByPerson(id, cpf);
		this.debitRepository.delete(debit);
	}
}
