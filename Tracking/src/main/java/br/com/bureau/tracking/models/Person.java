package br.com.bureau.tracking.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Component
public class Person implements Serializable, ICertificated {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String cpf;

	@Column(nullable = true, columnDefinition = "TEXT")
	private String name;

	@Column(nullable = true)
	private Date birth;
	
	@JsonIgnore
	@Column(columnDefinition = "TEXT")
	private String certificate;

	@PrePersist
	@PreUpdate
	private void onSave() {
		this.certificate = DigestUtils.md5DigestAsHex(this.toString().getBytes());
	}

	@Override
	public String toString() {
		return cpf + name;
	}

}
