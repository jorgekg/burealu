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

	@GetMapping("/{id}")
	public ResponseEntity<PersonDTO> get(@PathVariable Integer id) {
		return ResponseEntity.ok().body(this.personMappper.toDTO(this.personSerice.find(id)));
	}

	@PostMapping
	public ResponseEntity<PersonDTO> create(@Valid @RequestBody PersonDTO person) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.create(this.personMappper.toModel(person))));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<PersonDTO> update(@PathVariable Integer id, @RequestBody PersonDTO personDTO) {
		return ResponseEntity.ok()
				.body(this.personMappper.toDTO(this.personSerice.update(id, this.personMappper.toModel(personDTO))));
	}

}
