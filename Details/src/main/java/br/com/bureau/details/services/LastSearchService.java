package br.com.bureau.details.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bureau.details.exceptions.ObjectNotFoundException;
import br.com.bureau.details.models.LastSearch;
import br.com.bureau.details.repositories.LastSearchRepository;

@Service
public class LastSearchService {

	@Autowired
	private LastSearchRepository lastSearchRepository;
	
	public LastSearch find(Integer personId) {
		LastSearch lastSearch = this.lastSearchRepository.findByPersonId(personId);
		if (lastSearch == null) {
			throw new ObjectNotFoundException("Last search with a person id " + personId + " not found");
		}
		return lastSearch;
	}
	
	public LastSearch createOrUpdate(LastSearch lastSearch) {
		LastSearch search = null;
		try {
			search = this.find(lastSearch.getPersonId());
		} catch (ObjectNotFoundException e) {
			// ignore this error
		}
		if (search == null) {
			return this.doCreate(lastSearch);
		}
		return this.doUpdate(lastSearch, search);
	}
	
	private LastSearch doCreate(LastSearch lastSearch) {
		lastSearch.setLastSearch(new Date());
		return this.lastSearchRepository.save(lastSearch);
	}
	
	private LastSearch doUpdate(LastSearch lastSearch, LastSearch finded) {
		finded.setLastSearch(new Date());
		finded.setBureau(lastSearch.getBureau());
		finded.setDetails(lastSearch.getDetails());
		return this.lastSearchRepository.save(finded);
	}
	
}
