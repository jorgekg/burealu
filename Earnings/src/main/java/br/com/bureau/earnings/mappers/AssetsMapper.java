package br.com.bureau.earnings.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.AssetsDTO;
import br.com.bureau.earnings.models.Assets;

@Component
public class AssetsMapper {

	public AssetsDTO toDTO(Assets assets) {
		return new AssetsDTO(assets.getId(), assets.getAsstes(), assets.getPrice(), assets.getPaymentMethod());
	}

	public Assets toModel(AssetsDTO assetsDTO) {
		return new Assets(assetsDTO.getId(), null, assetsDTO.getAsstes(), assetsDTO.getPrice(), assetsDTO.getPaymentMethod(), null);
	}
	
}
