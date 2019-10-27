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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@ApiOperation(value = "Get person registered")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated")
	})
	@GetMapping("/{cpf}")
	public ResponseEntity<PersonDTO> get(@PathVariable String cpf) {
		return ResponseEntity.ok().body(this.personService.find(cpf));
	}
	
	@ApiOperation(value = "Update person")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@PutMapping("/{cpf}")
	public ResponseEntity<PersonDTO> update(@PathVariable String cpf, @Valid @RequestBody PersonDTO personDTO) {
		return ResponseEntity.ok().body(this.personService.update(cpf, personDTO));
	}
}
