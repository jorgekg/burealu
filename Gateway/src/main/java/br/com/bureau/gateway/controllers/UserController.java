package br.com.bureau.gateway.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bureau.gateway.dtos.UserDTO;
import br.com.bureau.gateway.mappers.UserMapper;
import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.models.enums.Role;
import br.com.bureau.gateway.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;

	@ApiOperation(value = "Create new user for access to API")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Create success"),
			@ApiResponse(code = 409, message = "This user has exists"),
			@ApiResponse(code = 400, message = "Fields required not found")
	})
	@PostMapping
	public ResponseEntity<UserDTO> create(@Valid @RequestBody User user) {
		return ResponseEntity.ok().body(UserMapper.toDTO(this.userService.create(user)));
	}
	
	@ApiOperation(value = "Change user password by token")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Change success"),
			@ApiResponse(code = 400, message = "Fields required not found")
	})
	@PutMapping("/password")
	public ResponseEntity<User> changePassword(@RequestBody User user) {
		return ResponseEntity.ok().body(this.userService.updatePassword(user));
	}
	
	@ApiOperation(value = "Assign your roles to someone else")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Change success")
	})
	@PutMapping("/{userId}/assign_role")
	public ResponseEntity<List<Role>> assignRole(@PathVariable Integer userId) {
		return ResponseEntity.ok().body(this.userService.assignRole(userId));
	}
	
}
