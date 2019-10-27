package br.com.bureau.details.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.details.dto.PersonDTO;
import br.com.bureau.details.exceptions.ObjectNotFoundException;
import br.com.bureau.details.models.LastBuy;
import br.com.bureau.details.models.Moviment;
import br.com.bureau.details.queues.GetPersonSender;
import br.com.bureau.details.repositories.LastBuyRepository;

@Service
public class LastBuyService {

	@Autowired
	private LastBuyRepository lastBuyRepository;
	
	@Autowired
	private MovimentService movimentService;
	
	@Autowired
	private GetPersonSender getPersonSender;
	
	public LastBuy find(String cpf) {
		PersonDTO person = this.getPersonSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Last buy of person with CPF " + cpf + " not found");
		}
		return this.find(person.getId());
	}
	
	public LastBuy find(Integer id) {
		LastBuy lastBuy = this.lastBuyRepository.findByPersonId(id);
		if (lastBuy == null) {
			throw new ObjectNotFoundException("Last buy of person with id " + id + " not found");
		}
		return lastBuy;
	}
	
	public LastBuy createOrUpdate(LastBuy lastBuy) {
		LastBuy search = null;
		try {
			search = this.find(lastBuy.getPersonId());
		} catch (ObjectNotFoundException e) {
			// ignore this error
		}
		if (search == null) {
			return this.doCreate(lastBuy);
		}
		return this.doUpdate(lastBuy, search);
	}
	
	@Transactional
	private LastBuy doCreate(LastBuy lastBuy) {
		lastBuy.setLastBuy(new Date());
		LastBuy buy = this.lastBuyRepository.save(lastBuy);
		this.movimentService.create(new Moviment(null, lastBuy.getPersonId(), new Date(), lastBuy.getDetails()));
		return buy;
	}
	
	private LastBuy doUpdate(LastBuy lastBuy, LastBuy finded) {
		finded.setLastBuy(new Date());
		finded.setDetails(lastBuy.getDetails());
		LastBuy buy = this.lastBuyRepository.save(finded);
		this.movimentService.create(new Moviment(null, finded.getPersonId(), new Date(), lastBuy.getDetails()));
		return buy;
	}
	
}
