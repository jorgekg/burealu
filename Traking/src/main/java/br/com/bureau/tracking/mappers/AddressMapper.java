package br.com.bureau.tracking.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.AddressDTO;
import br.com.bureau.tracking.models.Address;

@Component
public class AddressMapper {

	public Address toModel(AddressDTO addressDTO) {
		return new Address(addressDTO.getId(), null, addressDTO.getCity(), addressDTO.getNeighborhood(),
				addressDTO.getStreet(), addressDTO.getNumber(), null);
	}

	public AddressDTO toDTO(Address address) {
		return new AddressDTO(address.getId(), address.getCity(), address.getNeighborhood(), address.getStreet(),
				address.getNumber());
	}

}
