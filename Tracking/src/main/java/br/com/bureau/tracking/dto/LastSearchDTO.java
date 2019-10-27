package br.com.bureau.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastSearchDTO {

	private Integer personId;
	private String bureau;
	private String details;
	
}
