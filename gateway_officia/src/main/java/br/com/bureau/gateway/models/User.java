package br.com.bureau.gateway.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "person_id", nullable = true)
	private Integer personId;

	@NotEmpty(message = "E-mail is required")
	@NotNull(message = "E-mail is required")
	private String email;

	@NotEmpty(message = "Password is required")
	@NotNull(message = "Password is required")
	private String password;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Role> roles;
	
	private Date createAt;
	private Date updateAt;

	public User() {

	}

	public User(Integer id, String email, String password, Date createAt, Date updateAt) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.createAt = createAt;
		this.updateAt = updateAt;
	}
}
