package br.com.bureau.gateway.dtos;

import java.util.Date;
import java.util.List;

import br.com.bureau.gateway.models.User;
import br.com.bureau.gateway.models.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class userDTO {

	private Integer id;
	private Integer personId;
	private String email;
	private List<Role> roles;
	private Date createAt;
	private Date updateAt;
	
	public userDTO(User user) {
		this.id = user.getId();
		this.personId = user.getPersonId();
		this.email = user.getEmail();
		this.createAt = user.getCreateAt();
		this.updateAt = user.getUpdateAt();
	}
}
