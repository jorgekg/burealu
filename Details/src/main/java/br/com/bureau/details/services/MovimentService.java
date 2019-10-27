package br.com.bureau.details.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.details.dto.PersonDTO;
import br.com.bureau.details.exceptions.ObjectNotFoundException;
import br.com.bureau.details.models.Moviment;
import br.com.bureau.details.queues.GetPersonSender;
import br.com.bureau.details.repositories.MovimentRepository;

@Service
public class MovimentService {

	@Autowired
	private MovimentRepository movimentRepository;
	
	@Autowired
	private GetPersonSender getPersonSender;
	
	public Page<Moviment> find(String cpf, Integer page, Integer size) {
		PersonDTO person = this.getPersonSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Person with CPF " + cpf + " not found");
		}
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Moviment> moviment = this.movimentRepository.findByPersonId(person.getId(), pageRequest);
		if (moviment == null) {
			throw new ObjectNotFoundException("Moviment by person " + cpf + " not found");
		}
		return moviment;
	}
	
	public void create(Moviment moviment) {
		this.movimentRepository.save(moviment);
	}
	
}
