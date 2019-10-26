package br.com.bureau.earnings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.models.Income;
import br.com.bureau.earnings.queues.PersonSender;
import br.com.bureau.earnings.repositories.IncomeRepository;
import br.com.bureau.earnings.validators.CertificatedValidator;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;
	
	@Autowired
	private CertificatedValidator certificatedValidator;
	
	@Autowired
	private PersonSender personSender;
	
	public Income findByPerson(Integer id, Integer personId) {
		Income income = this.incomeRepository.findByIdAndPersonId(id, personId);
		if (income == null) {
			throw new ObjectNotFoundException("Income " + id + " not found");
		}
		this.certificatedValidator.validate(income);
		return income;
	}
	
	public Page<Income> list(Integer personId, Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return this.incomeRepository.findByPersonId(personId, pageRequest);
	}
	
	public Income create(Integer personId, Income income) {
		PersonDTO person = this.personSender.getPersonByTokenSync(personId.toString());
		if (person == null) {
			throw new ObjectNotFoundException("Person " + personId + " not found");
		}
		income.setPersonId(person.getId());
		return this.incomeRepository.save(income);
	}
	
	public Income update(Integer id, Integer personId, Income income) {
		Income incomeFinded = this.findByPerson(id, personId);
		incomeFinded.setCompany(income.getCompany());
		incomeFinded.setSalary(income.getSalary());
		return this.incomeRepository.save(income);
	}
	
	public void delete(Integer id, Integer personId) {
		this.incomeRepository.delete(this.findByPerson(id, personId));
	}
	
}
