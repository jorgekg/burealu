package br.com.bureau.earnings.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO {
	
	private Integer id;
	private String cpf;
	private String name;
	private Date birth;

}
