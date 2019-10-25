package br.com.bureau.tracking.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/peoples")
public class PersonController {

	@GetMapping
	public String get() {
		return "aqui";
	}
	
}
