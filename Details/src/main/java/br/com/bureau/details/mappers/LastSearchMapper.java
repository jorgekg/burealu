package br.com.bureau.details.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastSearchDTO;
import br.com.bureau.details.models.LastSearch;

@Component
public class LastSearchMapper {

	public LastSearch toModel(LastSearchDTO lastSearchDTO) {
		return new LastSearch(lastSearchDTO.getId(), lastSearchDTO.getPersonId(), lastSearchDTO.getLastSearch(), lastSearchDTO.getBureau());
	}
	
}
