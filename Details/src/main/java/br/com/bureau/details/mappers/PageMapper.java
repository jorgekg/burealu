package br.com.bureau.details.mappers;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class PageMapper<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<T> content;
	private long totalElements;
	private Integer totalPages;

	public PageMapper<T> toPage(Page<T> page) {
		this.content = page.getContent();
		this.totalElements = page.getTotalElements();
		this.totalPages = page.getTotalPages();
		return this;
	}
	
}
