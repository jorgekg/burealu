package br.com.bureau.gateway.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.bureau.gateway.models.enums.Role;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User extends Auditable implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Sequence code by user")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	@Column(name = "person_id", nullable = true)
	private Integer personId;

	@NotEmpty(message = "E-mail is required")
	@NotNull(message = "E-mail is required")
	private String email;

	@NotEmpty(message = "Password is required")
	@NotNull(message = "Password is required")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@ElementCollection(fetch = FetchType.EAGER)
	private List<Role> roles;
	
	@PrePersist
	private void addRoleByNewUser() {
		this.roles = new ArrayList<Role>();
		this.roles.add(Role.DETAILS);
	}

}
