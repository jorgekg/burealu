package br.com.bureau.tracking.services;

import java.util.Optional;

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

	public Address find(Integer id) {
		Optional<Address> optional = this.addressRepository.findById(id);
		if (!optional.isPresent()) {
			throw new ObjectNotFoundException("Address " + id + " not found");
		}
		return optional.get();
	}

	public Address findByPerson(Integer id, Integer personId) {
		Person person = this.personService.find(personId);
		Address address = this.addressRepository.findByIdAndPerson(id, person);
		if (address == null) {
			throw new ObjectNotFoundException("Address " + id + " not found");
		}
		this.lastSearchService.updateLastSearch(personId, "Search list addresses");
		return address;
	}

	public Page<Address> findAll(Integer personId, Integer page, Integer size) {
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<Address> pageAddress = this.addressRepository.findByPerson(this.personService.find(personId), pageRequest);
		pageAddress.getContent().stream().forEach(address -> {
			this.lastSearchService.updateLastSearch(personId, "Search list addresses");
			this.certificatedValidator.validate(address);
		});
		return pageAddress;
	}

	public Address create(Integer personId, Address address) {
		address.setPerson(this.personService.find(personId));
		return this.addressRepository.save(address);
	}

	public Address update(Integer id, Integer personId, Address address) {
		Address addressFinded = this.findByPerson(id, personId);
		addressFinded.setCity(address.getCity());
		addressFinded.setNeighborhood(address.getNeighborhood());
		addressFinded.setStreet(address.getStreet());
		addressFinded.setNumber(address.getNumber());
		return this.addressRepository.save(addressFinded);
	}

	public void delete(Integer id, Integer personId) {
		Address address = this.findByPerson(id, personId);
		this.addressRepository.delete(address);
	}

}
