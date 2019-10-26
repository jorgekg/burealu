package br.com.bureau.earnings.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.earnings.dto.IncomeDTO;
import br.com.bureau.earnings.mappers.IncomeMapper;
import br.com.bureau.earnings.mappers.PageMapper;
import br.com.bureau.earnings.models.Income;
import br.com.bureau.earnings.services.IncomeService;

@RestController
@RequestMapping("/people/{personId}/incomes")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private IncomeMapper incomeMapper;

	@Autowired
	private PageMapper<Income> pageMapper;

	@GetMapping()
	private ResponseEntity<PageMapper<Income>> list(@PathVariable Integer personId, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.incomeService.list(personId, page, size)));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<IncomeDTO> get(@PathVariable Integer personId, @PathVariable Integer id) {
		return ResponseEntity.ok().body(this.incomeMapper.toDTO(this.incomeService.findByPerson(id, personId)));
	}

	@PostMapping
	public ResponseEntity<IncomeDTO> create(@PathVariable Integer personId, @Valid @RequestBody IncomeDTO incomeDTO) {
		return ResponseEntity.ok().body(
				this.incomeMapper.toDTO(this.incomeService.create(personId, this.incomeMapper.toModel(incomeDTO))));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<IncomeDTO> update(@PathVariable Integer personId, @PathVariable Integer id,
			@Valid @RequestBody IncomeDTO incomeDTO) {
		return ResponseEntity.ok().body(
				this.incomeMapper.toDTO(this.incomeService.update(id, personId, this.incomeMapper.toModel(incomeDTO))));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer personId, @PathVariable Integer id) {
		this.incomeService.delete(id, personId);
		return ResponseEntity.ok().build();
	}
}
