package br.com.bureau.details.dto;

import java.util.Date;
import java.util.List;

import br.com.bureau.details.models.enuns.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastBuyDTO {

	private Integer id;

	private Integer personId;

	private Date lastBuy;

	private String details;

	private List<PaymentMethod> methods;
}
