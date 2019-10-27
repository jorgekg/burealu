package br.com.bureau.details.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.details.models.Moviment;

@Repository
public interface MovimentRepository extends JpaRepository<Moviment, Integer> {

	Page<Moviment> findByPersonId(Integer personId, Pageable pageable);
	
}
