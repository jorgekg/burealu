package br.com.bureau.tracking.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bureau.tracking.models.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DebitDTO {

	private Integer id;
	
	private Person person;
	
	@NotNull(message = "Company is required")
	@NotEmpty(message = "Comapny is requred")
	private String company;
	
	@NotNull(message = "Price is required")
	@NotEmpty(message = "Price is requred")
	private Double price;
}
