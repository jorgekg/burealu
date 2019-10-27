package br.com.bureau.details.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastSearchDTO;
import br.com.bureau.details.models.LastSearch;

@Component
public class LastSearchMapper {

	public LastSearch toModel(LastSearchDTO lastSearchDTO) {
		return new LastSearch(lastSearchDTO.getId(), lastSearchDTO.getPersonId(), lastSearchDTO.getLastSearch(),
				lastSearchDTO.getDetails(), lastSearchDTO.getBureau());
	}

	public LastSearchDTO toDTO(LastSearch lastSearch) {
		return new LastSearchDTO(lastSearch.getId(), lastSearch.getPersonId(), lastSearch.getLastSearch(),
				lastSearch.getDetails(), lastSearch.getBureau());
	}

}
