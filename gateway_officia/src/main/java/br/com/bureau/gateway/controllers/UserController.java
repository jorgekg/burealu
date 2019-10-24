package br.com.bureau.gateway.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.gateway.dtos.userDTO;
import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<userDTO> create(@Valid @RequestBody User user) {
		return ResponseEntity.ok().body(new userDTO(this.userService.create(user)));
	}
	
}
