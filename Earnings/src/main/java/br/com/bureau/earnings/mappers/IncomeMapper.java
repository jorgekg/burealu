package br.com.bureau.earnings.mappers;

import org.springframework.stereotype.Component;

import br.com.bureau.earnings.dto.IncomeDTO;
import br.com.bureau.earnings.models.Income;

@Component
public class IncomeMapper {

	public IncomeDTO toDTO(Income income) {
		return new IncomeDTO(income.getId(), income.getPersonId(), income.getCompany(), income.getSalary());
	}
	
	public Income toModel(IncomeDTO incomeDTO) {
		return new Income(incomeDTO.getId(), incomeDTO.getPersonId(), incomeDTO.getCompany(), incomeDTO.getSalary(), null);
	}
	
}
