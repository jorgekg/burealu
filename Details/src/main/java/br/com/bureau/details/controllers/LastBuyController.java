package br.com.bureau.details.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.details.dto.LastBuyDTO;
import br.com.bureau.details.mappers.LastBuyMapper;
import br.com.bureau.details.services.LastBuyService;

@RestController
@RequestMapping("/people/{personId}/last_buy")
public class LastBuyController {
	
	@Autowired
	private LastBuyService lastBuyService;
	
	@Autowired
	private LastBuyMapper lastBuyMapper;
	
	@GetMapping
	public ResponseEntity<LastBuyDTO> get(@PathVariable Integer personId) {
		return ResponseEntity.ok().body(this.lastBuyMapper.toDTO(this.lastBuyService.find(personId)));
	}

}
