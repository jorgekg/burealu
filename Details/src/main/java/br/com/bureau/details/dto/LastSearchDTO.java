package br.com.bureau.details.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastSearchDTO {

	private Integer id;
	private Integer personId;
	private Date lastSearch;
	private String bureau;
	
}
