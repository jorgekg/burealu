package br.com.bureau.details.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.details.models.LastSearch;

@Repository
public interface LastSearchRepository extends JpaRepository<LastSearch, Integer> {

	LastSearch findByPersonId(Integer personId);
	
}
