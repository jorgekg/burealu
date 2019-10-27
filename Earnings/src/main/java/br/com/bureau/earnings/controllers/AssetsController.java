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

import br.com.bureau.earnings.dto.AssetsDTO;
import br.com.bureau.earnings.mappers.AssetsMapper;
import br.com.bureau.earnings.mappers.PageMapper;
import br.com.bureau.earnings.models.Assets;
import br.com.bureau.earnings.services.AssetsService;

@RestController
@RequestMapping("/people/{cpf}/asstes")
public class AssetsController {

	@Autowired
	private AssetsService assetsService;

	@Autowired
	private AssetsMapper assetsMapper;
	
	@Autowired
	private PageMapper<Assets> pageMapper;

	@GetMapping()
	private ResponseEntity<PageMapper<Assets>> list(@PathVariable String cpf, @RequestParam(defaultValue = "0") Integer page,
			@RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.assetsService.list(cpf, page, size)));
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<AssetsDTO> get(@PathVariable String cpf, @PathVariable Integer id) {
		return ResponseEntity.ok().body(this.assetsMapper.toDTO(this.assetsService.findByPerson(id, cpf)));
	}

	@PostMapping
	public ResponseEntity<AssetsDTO> create(@PathVariable String cpf, @Valid @RequestBody AssetsDTO assetsDTO) {
		return ResponseEntity.ok().body(
				this.assetsMapper.toDTO(this.assetsService.create(cpf, this.assetsMapper.toModel(assetsDTO))));
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<AssetsDTO> update(@PathVariable String cpf, @PathVariable Integer id,
			@Valid @RequestBody AssetsDTO assetsDTO) {
		return ResponseEntity.ok().body(
				this.assetsMapper.toDTO(this.assetsService.update(id, cpf, this.assetsMapper.toModel(assetsDTO))));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable String cpf, @PathVariable Integer id) {
		this.assetsService.delete(id, cpf);
		return ResponseEntity.ok().build();
	}
}
