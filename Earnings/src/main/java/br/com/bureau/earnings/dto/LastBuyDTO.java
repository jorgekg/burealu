package br.com.bureau.earnings.dto;

import lombok.AllArgsConstructor;

import lombok.Setter;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LastBuyDTO {

	private Integer personId;
	private String details;
	
}
