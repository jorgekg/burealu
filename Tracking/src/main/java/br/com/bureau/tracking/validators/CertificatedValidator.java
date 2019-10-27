package br.com.bureau.tracking.validators;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import br.com.bureau.tracking.models.ICertificated;

@Component
public class CertificatedValidator {

	public void validate(ICertificated object) {
		String certificated = object.getCertificate();
		String cryp = DigestUtils.md5DigestAsHex(object.toString().getBytes());
		if (!certificated.equals(cryp)) {
			throw new DataIntegrityViolationException("This person has integrity violed");
		}
	}

}
