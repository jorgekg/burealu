package br.com.bureau.details.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.details.models.LastBuy;

@Repository
public interface LastBuyRepository extends JpaRepository<LastBuy, Integer> {

	LastBuy findByPersonId(Integer personId);
	
}
