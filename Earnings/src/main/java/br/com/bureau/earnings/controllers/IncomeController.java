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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/people/{cpf}/incomes")
public class IncomeController {

	@Autowired
	private IncomeService incomeService;

	@Autowired
	private IncomeMapper incomeMapper;

	@Autowired
	private PageMapper<Income> pageMapper;

	@ApiOperation(value = "List all incomes registered of person")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated")
	})
	@GetMapping()
	private ResponseEntity<PageMapper<Income>> list(@PathVariable String cpf, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.incomeService.list(cpf, page, size)));
	}

	@ApiOperation(value = "Get income registered")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
			@ApiResponse(code = 404, message = "Income not fount for CPF")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<IncomeDTO> get(@PathVariable String cpf, @PathVariable Integer id) {
		return ResponseEntity.ok().body(this.incomeMapper.toDTO(this.incomeService.findByPerson(id, cpf)));
	}

	@ApiOperation(value = "Create new income")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Create success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@PostMapping
	public ResponseEntity<IncomeDTO> create(@PathVariable String cpf, @Valid @RequestBody IncomeDTO incomeDTO) {
		return ResponseEntity.ok().body(
				this.incomeMapper.toDTO(this.incomeService.create(cpf, this.incomeMapper.toModel(incomeDTO))));
	}

	@ApiOperation(value = "Update income")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
			@ApiResponse(code = 404, message = "Income not fount for CPF")
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<IncomeDTO> update(@PathVariable String cpf, @PathVariable Integer id,
			@Valid @RequestBody IncomeDTO incomeDTO) {
		return ResponseEntity.ok().body(
				this.incomeMapper.toDTO(this.incomeService.update(id, cpf, this.incomeMapper.toModel(incomeDTO))));
	}

	@ApiOperation(value = "Delete income")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
			@ApiResponse(code = 404, message = "Income not fount for CPF")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable String cpf, @PathVariable Integer id) {
		this.incomeService.delete(id, cpf);
		return ResponseEntity.ok().build();
	}
}
