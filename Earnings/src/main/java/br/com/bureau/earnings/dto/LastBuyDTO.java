package br.com.bureau.earnings.dto;

import java.util.List;

import br.com.bureau.earnings.models.enuns.PaymentMethod;
import lombok.AllArgsConstructor;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastBuyDTO {

	private Integer personId;
	private String details;
	private List<PaymentMethod> paymentMethods;
	
}
