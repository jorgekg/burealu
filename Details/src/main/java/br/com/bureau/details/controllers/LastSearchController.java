package br.com.bureau.details.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.details.dto.LastSearchDTO;
import br.com.bureau.details.mappers.LastSearchMapper;
import br.com.bureau.details.services.LastSearchService;

@RestController
@RequestMapping("/people/{cpf}/last_search")
public class LastSearchController {
	
	@Autowired
	private LastSearchService lastSearchService;
	
	@Autowired
	private LastSearchMapper lastSearchMapper;
	
	@GetMapping
	public ResponseEntity<LastSearchDTO> get(@PathVariable String cpf) {
		return ResponseEntity.ok().body(this.lastSearchMapper.toDTO(this.lastSearchService.find(cpf)));
	}

}
