package br.com.bureau.earnings.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.exceptions.ObjectNotFoundException;
import br.com.bureau.earnings.models.Assets;
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
	
	public Assets findByPerson(Integer id, String cpf) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		Assets assets = this.assetsRepository.findByIdAndPersonId(id, person.getId());
		if (assets == null) {
			throw new ObjectNotFoundException("Assets " + id + " not foud");
		}
		return assets;
	}
	
	public Page<Assets> list(String cpf, Integer page, Integer size) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		PageRequest pageRequest = PageRequest.of(page, size);
		return this.assetsRepository.findByPersonId(person.getId(), pageRequest);
	}
	
	@Transactional
	public Assets create(String cpf, Assets assets) {
		PersonDTO person = this.personSender.getPersonByCPFSync(cpf);
		if (person == null) {
			throw new ObjectNotFoundException("Person with cpf " + cpf + " not found");
		}
		assets.setPersonId(person.getId());
		this.lastBuyService.lastBuyUpdate(person.getId(), "Buy " + assets.getAsstes(), assets.getPaymentMethod());
		return this.assetsRepository.save(assets);
	}
	
	@Transactional
	public Assets update(Integer id, String cpf, Assets assets) {
		Assets assetsFinded = this.findByPerson(id, cpf);
		assetsFinded.setPrice(assets.getPrice());
		assetsFinded.setAsstes(assets.getAsstes());
		assetsFinded.setPaymentMethod(assets.getPaymentMethod());
		this.lastBuyService.lastBuyUpdate(assets.getPersonId(), "Updated " + assets.getAsstes(), assets.getPaymentMethod());
		this.assetsRepository.save(assetsFinded);
		return assetsFinded;
	}
	
	public void delete(Integer id, String cpf) {
		this.assetsRepository.delete(this.findByPerson(id, cpf));
	}

}
