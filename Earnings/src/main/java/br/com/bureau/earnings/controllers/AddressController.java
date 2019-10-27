package br.com.bureau.earnings.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.earnings.dto.AddressDTO;
import br.com.bureau.earnings.services.AddressService;

@RestController
@RequestMapping("/people/{cpf}/addresses")
public class AddressController {
	
	@Autowired
	private AddressService addressService;

	@PostMapping
	public ResponseEntity<AddressDTO> create(@PathVariable String cpf, @Valid @RequestBody AddressDTO addressDTO) {
		return ResponseEntity.ok().body(this.addressService.create(cpf, addressDTO));
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<AddressDTO> update(@PathVariable String cpf, @PathVariable Integer id, @Valid @RequestBody AddressDTO addressDTO) {
		return ResponseEntity.ok().body(this.addressService.update(cpf, id, addressDTO));
	}
}
