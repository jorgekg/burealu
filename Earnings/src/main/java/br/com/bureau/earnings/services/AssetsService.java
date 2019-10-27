package br.com.bureau.earnings.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.models.Assets;
import br.com.bureau.earnings.models.enuns.PaymentMethod;
import br.com.bureau.earnings.queues.GetPersonSender;
import br.com.bureau.earnings.repositories.AssetsRepository;

@Service
public class AssetsService {
	
	@Autowired
	private AssetsRepository assetsRepository;
	
	@Autowired
	private LastBuyService lastBuyService;
	
	@Autowired
	private GetPersonSender personSender;
	
	public Assets findByPerson(Integer id, Integer personId) {
		Assets assets = this.assetsRepository.findByIdAndPersonId(id, personId);
		if (assets == null) {
			throw new ObjectNotFoundException("Assets " + id + " not foud");
		}
		return assets;
	}
	
	public Page<Assets> list(Integer personId, Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		return this.assetsRepository.findByPersonId(personId, pageRequest);
	}
	
	@Transactional
	public Assets create(Integer personId, Assets assets) {
		PersonDTO person = this.personSender.getPersonByTokenSync(personId.toString());
		if (person == null) {
			throw new ObjectNotFoundException("Person " + personId + " not found");
		}
		assets.setPersonId(person.getId());
		if (assets.getPaymentMethod().contains(PaymentMethod.CREDIT_CARD)) {
			this.lastBuyService.lastBuyUpdate(personId, "Buy " + assets.getAsstes());
		}
		return this.assetsRepository.save(assets);
	}
	
	@Transactional
	public Assets update(Integer id, Integer personId, Assets assets) {
		Assets assetsFinded = this.findByPerson(id, personId);
		assetsFinded.setPrice(assets.getPrice());
		assetsFinded.setAsstes(assets.getAsstes());
		assetsFinded.setPaymentMethod(assets.getPaymentMethod());
		if (assets.getPaymentMethod().contains(PaymentMethod.CREDIT_CARD)) {
			this.lastBuyService.lastBuyUpdate(personId, "Buy " + assets.getAsstes());
		}
		this.assetsRepository.save(assetsFinded);
		return assetsFinded;
	}
	
	public void delete(Integer id, Integer personId) {
		this.assetsRepository.delete(this.findByPerson(id, personId));
	}

}
