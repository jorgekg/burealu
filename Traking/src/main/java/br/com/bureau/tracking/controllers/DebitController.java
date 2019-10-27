package br.com.bureau.tracking.controllers;

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

import br.com.bureau.tracking.dto.DebitDTO;
import br.com.bureau.tracking.mappers.DebitMapper;
import br.com.bureau.tracking.mappers.PageMapper;
import br.com.bureau.tracking.models.Debit;
import br.com.bureau.tracking.services.DebitService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/people/{cpf}/debits")
public class DebitController {

	@Autowired
	private DebitService debitService;

	@Autowired
	private DebitMapper debitMapper;

	@Autowired
	private PageMapper<Debit> pageMapper;

	@ApiOperation(value = "List all debit registered with pagination and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<DebitDTO> find(@PathVariable String cpf, @PathVariable Integer id) {
		return ResponseEntity.ok().body(this.debitMapper.toDTO(this.debitService.findByPerson(id, cpf, true)));
	}

	@ApiOperation(value = "Get debit registered and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@GetMapping
	public ResponseEntity<PageMapper<Debit>> list(@PathVariable String cpf,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.debitService.findAll(cpf, page, size)));
	}

	@ApiOperation(value = "Create new debit")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Create success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
	})
	@PostMapping
	public ResponseEntity<DebitDTO> create(@PathVariable String cpf, @Valid @RequestBody DebitDTO debitDTO) {
		return ResponseEntity.ok()
				.body(this.debitMapper.toDTO(this.debitService.create(cpf, this.debitMapper.toModel(debitDTO))));
	}

	@ApiOperation(value = "Update debit")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<DebitDTO> update(@PathVariable String cpf, @PathVariable Integer id,
			@Valid @RequestBody DebitDTO debitDTO) {
		return ResponseEntity.ok().body(
				this.debitMapper.toDTO(this.debitService.update(id, cpf, this.debitMapper.toModel(debitDTO))));
	}
	
	@ApiOperation(value = "Delete debit")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<DebitDTO> delete(@PathVariable String cpf, @PathVariable Integer id) {
		this.debitService.delete(id, cpf);
		return ResponseEntity.ok().build();
	}
}
