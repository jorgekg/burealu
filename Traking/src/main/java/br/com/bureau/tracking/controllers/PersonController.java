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

@RestController
@RequestMapping("/people")
public class PersonController {

	@Autowired
	private PersonService personSerice;

	@Autowired
	private PersonMapper personMappper;
	
	@Autowired
	private PageMapper<Person> pageMapper;
			
	@GetMapping
	public ResponseEntity<PageMapper<Person>> list(@RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.personSerice.findAll(page, size)));
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<PersonDTO> get(@PathVariable String cpf) {
		return ResponseEntity.ok().body(this.personMappper.toDTO(this.personSerice.findByCPF(cpf, true)));
	}

	@PostMapping
	public ResponseEntity<PersonDTO> create(@Valid @RequestBody PersonDTO person) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.create(this.personMappper.toModel(person))));
	}

	@PutMapping(value = "/{cpf}")
	public ResponseEntity<PersonDTO> update(@PathVariable String cpf, @RequestBody PersonDTO personDTO) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.update(cpf, this.personMappper.toModel(personDTO))));
	}

}
