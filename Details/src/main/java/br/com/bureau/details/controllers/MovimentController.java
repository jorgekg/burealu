package br.com.bureau.details.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.details.mappers.PageMapper;
import br.com.bureau.details.models.Moviment;
import br.com.bureau.details.services.MovimentService;

@RestController
@RequestMapping("people/{cpf}/moviments")
public class MovimentController {

	@Autowired
	private MovimentService movimentService;
	
	@Autowired
	private PageMapper<Moviment> pageMapper;
	
	@GetMapping
	public ResponseEntity<PageMapper<Moviment>> list(@PathVariable String cpf,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return ResponseEntity.ok().body(this.pageMapper.toPage(this.movimentService.find(cpf, page, size)));
	}
	
}
