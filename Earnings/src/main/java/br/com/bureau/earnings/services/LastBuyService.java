package br.com.bureau.earnings.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.earnings.dto.LastBuyDTO;
import br.com.bureau.earnings.models.enuns.PaymentMethod;
import br.com.bureau.earnings.queues.UpdateLastBuySender;

@Service
public class LastBuyService {

	@Autowired
	private UpdateLastBuySender updateLastBuySender;
	
	public void lastBuyUpdate(Integer personId, String details, List<PaymentMethod> paymentMethods) {
		this.updateLastBuySender.sendAsync(new LastBuyDTO(personId, details, paymentMethods));
	}
	
}
