package br.com.bureau.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import br.com.bureau.tracking.exceptions.ObjectNotFoundException;
import br.com.bureau.tracking.models.Address;
import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.repositories.AddressRepository;
import br.com.bureau.tracking.validators.CertificatedValidator;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private PersonService personService;

	@Autowired
	private LastSearchService lastSearchService;

	@Autowired
	private CertificatedValidator certificatedValidator;
	
	public Address findByPerson(Integer id, String cpf) {
		return this.findByPerson(id, cpf, false);
	}

	public Address findByPerson(Integer id, String cpf, Boolean isUpdateLastSearch) {
		Person person = this.personService.findByCPF(cpf);
		Address address = this.addressRepository.findByIdAndPerson(id, person);
		if (address == null) {
			throw new ObjectNotFoundException("Address " + id + " not found");
		}
		this.certificatedValidator.validate(address);
		if (isUpdateLastSearch) {
			this.lastSearchService.updateLastSearch(person.getId(), "Search list addresses");
		}
		return address;
	}

	public Page<Address> findAll(String cpf, Integer page, Integer size) {
		Person person = this.personService.findByCPF(cpf);
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Address> pageAddress = this.addressRepository.findByPerson(person, pageRequest);
		pageAddress.getContent().stream().forEach(address -> {
			this.lastSearchService.updateLastSearch(person.getId(), "Search list addresses");
			this.certificatedValidator.validate(address);
		});
		return pageAddress;
	}

	public Address create(String cpf, Address address) {
		address.setPerson(this.personService.findByCPF(cpf));
		return this.addressRepository.save(address);
	}

	public Address update(Integer id, String cpf, Address address) {
		Address addressFinded = this.findByPerson(id, cpf);
		addressFinded.setCity(address.getCity());
		addressFinded.setNeighborhood(address.getNeighborhood());
		addressFinded.setStreet(address.getStreet());
		addressFinded.setNumber(address.getNumber());
		return this.addressRepository.save(addressFinded);
	}

	public void delete(Integer id, String cpf) {
		Address address = this.findByPerson(id, cpf);
		this.addressRepository.delete(address);
	}

}
