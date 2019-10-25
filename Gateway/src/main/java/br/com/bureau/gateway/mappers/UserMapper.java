package br.com.bureau.gateway.mappers;

import br.com.bureau.gateway.dtos.userDTO;
import br.com.bureau.gateway.models.User;

public class UserMapper {

	public static userDTO toDTO(User user) {
		return new userDTO(user.getId(), user.getPersonId(), user.getEmail(), user.getRoles(), user.getCreateAt(), user.getUpdateAt());
	}
	
}
