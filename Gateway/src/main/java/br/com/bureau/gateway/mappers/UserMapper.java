package br.com.bureau.gateway.mappers;

import br.com.bureau.gateway.dtos.UserDTO;
import br.com.bureau.gateway.models.User;

public class UserMapper {

	public static UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getPersonId(), user.getEmail(), user.getRoles(), user.getCreateAt(), user.getUpdateAt());
	}
	
}
