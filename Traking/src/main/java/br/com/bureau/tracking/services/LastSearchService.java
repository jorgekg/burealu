package br.com.bureau.tracking.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.com.bureau.tracking.dto.LastSearchDTO;
import br.com.bureau.tracking.dto.UserDTO;
import br.com.bureau.tracking.queue.UpdateLastSearchSender;

@Service
public class LastSearchService {
	
	@Autowired
	private UpdateLastSearchSender updateLastSearchSender;
	
	public void updateLastSearch(Integer personId, String search) {
		UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		this.updateLastSearchSender.sendAsync(new LastSearchDTO(personId, user.getEmail(), search));
	}

}
