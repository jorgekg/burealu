package br.com.bureau.earnings.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bureau.earnings.models.enuns.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetsDTO {

	private Integer id;
	
	@NotNull(message = "Assets is required")
	@NotEmpty(message = "Assets is required")
	private String asstes;
	
	@NotNull(message = "Price is required")
	private Double price;
	
	@NotNull(message = "Price is required")
	private List<PaymentMethod> paymentMethod;
	
}
