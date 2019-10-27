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
public class AddressDTO {
	
	private Integer id;
	
	@NotNull(message = "City is required")
	@NotEmpty(message = "City is required")
	private String city;
	
	@NotNull(message = "Neighborhood is required")
	@NotEmpty(message = "Neighborhood is required")
	private String neighborhood;
	
	@NotNull(message = "street is required")
	@NotEmpty(message = "Street is required")
	private String street;
	
	@NotNull(message = "Number is required")
	@NotEmpty(message = "Number is required")
	private String number;

}