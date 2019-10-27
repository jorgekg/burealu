package br.com.bureau.details.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.details.exceptions.ObjectNotFoundException;
import br.com.bureau.details.models.LastBuy;
import br.com.bureau.details.repositories.LastBuyRepository;

@Service
public class LastBuyService {

	@Autowired
	private LastBuyRepository lastBuyRepository;
	
	public LastBuy find(Integer personId) {
		LastBuy lastBuy = this.lastBuyRepository.findByPersonId(personId);
		if (lastBuy == null) {
			throw new ObjectNotFoundException("Last buy with a person id " + personId + " not found");
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
	
	private LastBuy doCreate(LastBuy lastBuy) {
		lastBuy.setLastBuy(new Date());
		return this.lastBuyRepository.save(lastBuy);
	}
	
	private LastBuy doUpdate(LastBuy lastBuy, LastBuy finded) {
		finded.setLastBuy(new Date());
		finded.setDetails(lastBuy.getDetails());
		return this.lastBuyRepository.save(finded);
	}
	
}
