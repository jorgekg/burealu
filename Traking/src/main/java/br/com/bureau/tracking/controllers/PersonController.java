package br.com.bureau.tracking.controllers;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.tracking.dto.PersonDTO;
import br.com.bureau.tracking.mappers.PageMapper;
import br.com.bureau.tracking.mappers.PersonMapper;
import br.com.bureau.tracking.models.Person;
import br.com.bureau.tracking.services.PersonService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonService personSerice;

	@Autowired
	private PersonMapper personMappper;
	
	@Autowired
	private PageMapper<Person> pageMapper;
			
	@ApiOperation(value = "List all person registered with pagination and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated")
	})
	@GetMapping
	public ResponseEntity<PageMapper<Person>> list(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.personSerice.findAll(page, size)));
	}

	@ApiOperation(value = "Get person registered and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@GetMapping("/{cpf}")
	public ResponseEntity<PersonDTO> get(@PathVariable String cpf) {
		return ResponseEntity.ok().body(this.personMappper.toDTO(this.personSerice.findByCPF(cpf, true)));
	}

	@ApiOperation(value = "Create new person")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Create success"),
			@ApiResponse(code = 409, message = "This CPF has exists"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@PostMapping
	public ResponseEntity<PersonDTO> create(@Valid @RequestBody PersonDTO person) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.create(this.personMappper.toModel(person))));
	}

	@ApiOperation(value = "Update person")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@PutMapping(value = "/{cpf}")
	public ResponseEntity<PersonDTO> update(@PathVariable String cpf, @RequestBody PersonDTO personDTO) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.update(cpf, this.personMappper.toModel(personDTO))));
	}

}
