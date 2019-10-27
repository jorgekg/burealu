package br.com.bureau.details.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.details.dto.LastBuyDTO;
import br.com.bureau.details.models.LastBuy;

@Component
public class LastBuyMapper {

	public LastBuy toModel(LastBuyDTO lastBuyDTO) {
		return new LastBuy(lastBuyDTO.getId(), lastBuyDTO.getPersonId(), lastBuyDTO.getLastBuy(),
				lastBuyDTO.getDetails());
	}

	public LastBuyDTO toDTO(LastBuy lastBuy) {
		return new LastBuyDTO(lastBuy.getId(), lastBuy.getPersonId(), lastBuy.getLastBuy(), lastBuy.getDetails(), null);
	}

}
