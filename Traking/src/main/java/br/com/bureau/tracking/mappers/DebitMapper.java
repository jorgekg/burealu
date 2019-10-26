package br.com.bureau.tracking.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.tracking.dto.DebitDTO;
import br.com.bureau.tracking.models.Debit;

@Component
public class DebitMapper {

	public Debit toModel(DebitDTO debitDTO) {
		return new Debit(debitDTO.getId(), debitDTO.getPerson(), debitDTO.getCompany(), debitDTO.getPrice().toString(),
				null);
	}

	public DebitDTO toDTO(Debit debit) {
		return new DebitDTO(debit.getId(), debit.getPerson(), debit.getCompany(), Double.parseDouble(debit.getPrice()));
	}
}
