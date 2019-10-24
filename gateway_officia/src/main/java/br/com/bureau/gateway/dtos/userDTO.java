package br.com.bureau.gateway.dtos;

import java.util.Date;
import java.util.List;
import br.com.bureau.gateway.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class userDTO {

	private Integer id;
	private Integer personId;
	private String email;
	private List<Role> roles;
	private Date createAt;
	private Date updateAt;

}
