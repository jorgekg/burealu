package br.com.bureau.tracking.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonDTO {
	
	private Integer id;
	
	@NotNull(message = "CPF is required")
	@NotEmpty(message = "CPF is required")
	private String cpf;
	
	@NotNull(message = "Name is required")
	@NotEmpty(message = "Name is required")
	private String name;
	
}
