package br.com.bureau.earnings.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.models.Income;
import br.com.bureau.earnings.queues.GetPersonSender;
import br.com.bureau.earnings.repositories.IncomeRepository;
import br.com.bureau.earnings.validators.CertificatedValidator;

@Service
public class IncomeService {

	@Autowired
	private IncomeRepository incomeRepository;
	
	@Autowired
	private CertificatedValidator certificatedValidator;
	
	@Autowired
	private GetPersonSender personSender;
	
	public Income findByPerson(Integer id, String cpf) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Person with cpf " + cpf+ " not found");
		}
		Income income = this.incomeRepository.findByIdAndPersonId(id, person.getId());
		if (income == null) {
			throw new ObjectNotFoundException("Income " + id + " not found");
		}
		this.certificatedValidator.validate(income);
		return income;
	}
	
	public Page<Income> list(String cpf, Integer page, Integer size) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Person with cpf " + cpf+ " not found");
		}
		PageRequest pageRequest = PageRequest.of(page, size);
		return this.incomeRepository.findByPersonId(person.getId(), pageRequest);
	}
	
	public Income create(String cpf, Income income) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Person with CPF " + cpf + " not found");
		}
		income.setPersonId(person.getId());
		return this.incomeRepository.save(income);
	}
	
	public Income update(Integer id, String cpf, Income income) {
		Income incomeFinded = this.findByPerson(id, cpf);
		incomeFinded.setCompany(income.getCompany());
		incomeFinded.setSalary(income.getSalary());
		return this.incomeRepository.save(income);
	}
	
	public void delete(Integer id, String cpf) {
		this.incomeRepository.delete(this.findByPerson(id, cpf));
	}
	
}
