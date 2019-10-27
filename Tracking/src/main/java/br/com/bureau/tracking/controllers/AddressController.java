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

import br.com.bureau.tracking.dto.AddressDTO;
import br.com.bureau.tracking.mappers.AddressMapper;
import br.com.bureau.tracking.mappers.PageMapper;
import br.com.bureau.tracking.models.Address;
import br.com.bureau.tracking.services.AddressService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/people/{cpf}/addresses")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private AddressMapper addressMapper;

	@Autowired
	private PageMapper<Address> pageMapper;
	
	@ApiOperation(value = "List all address registered with pagination and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated")
	})
	@GetMapping(value = "/{id}")
	public ResponseEntity<AddressDTO> find(@PathVariable String cpf, @PathVariable Integer id) {
		return ResponseEntity.ok().body(this.addressMapper.toDTO(this.addressService.findByPerson(id, cpf, true)));
	}

	@ApiOperation(value = "Get address registered and register last search")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "List success"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF")
	})
	@GetMapping
	public ResponseEntity<PageMapper<Address>> list(@PathVariable String cpf,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.addressService.findAll(cpf, page, size)));
	}

	@ApiOperation(value = "Create new address")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Create success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "Person not fount for CPF"),
	})
	@PostMapping
	public ResponseEntity<AddressDTO> create(@PathVariable String cpf,
			@Valid @RequestBody AddressDTO addressDTO) {
		return ResponseEntity.ok().body(
				this.addressMapper.toDTO(this.addressService.create(cpf, this.addressMapper.toModel(addressDTO))));
	}

	@ApiOperation(value = "Update address")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Update success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "address not fount for CPF"),
	})
	@PutMapping(value = "/{id}")
	public ResponseEntity<AddressDTO> update(@PathVariable String cpf, @PathVariable Integer id,
			@Valid @RequestBody AddressDTO addressDTO) {
		return ResponseEntity.ok().body(this.addressMapper
				.toDTO(this.addressService.update(id, cpf, this.addressMapper.toModel(addressDTO))));
	}
	
	@ApiOperation(value = "Delete address")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Delete success"),
			@ApiResponse(code = 400, message = "Fields required not found"),
			@ApiResponse(code = 401, message = "User not authorized"),
			@ApiResponse(code = 403, message = "User not authenticated"),
			@ApiResponse(code = 404, message = "address not fount for CPF")
	})
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable String cpf, @PathVariable Integer id) {
		this.addressService.delete(id, cpf);
		return ResponseEntity.ok().build();
	}

}
