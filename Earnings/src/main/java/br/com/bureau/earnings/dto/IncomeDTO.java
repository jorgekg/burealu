package br.com.bureau.earnings.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDTO {

	private Integer id;

	private Integer personId;

	@NotEmpty(message = "Company is required")
	@NotNull(message = "Company is required")
	private String company;

	@NotNull(message = "Salary is required")
	private Double salary;
}
