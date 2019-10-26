package br.com.bureau.earnings.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.earnings.dto.PersonDTO;
import br.com.bureau.earnings.services.PersonService;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> get(@PathVariable Integer id) {
		return ResponseEntity.ok().body(this.personService.find(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Integer id, @Valid @RequestBody PersonDTO personDTO) {
		return ResponseEntity.ok().body(this.personService.update(id, personDTO));
	}
}
