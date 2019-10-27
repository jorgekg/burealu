package br.com.bureau.earnings.controllers;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.earnings.dto.AddressDTO;

@RestController
@RequestMapping("/people/{cpf}/addresses")
public class AddressController {

	@PostMapping
	public ResponseEntity<AddressDTO> create(@PathVariable String cpf, @Valid @RequestBody AddressDTO addressDTO) {
		
	}
	
}
