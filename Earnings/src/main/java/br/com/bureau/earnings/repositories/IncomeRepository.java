package br.com.bureau.earnings.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.earnings.models.Income;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Integer>{

	Income findByIdAndPersonId(Integer id, Integer personId);
	Page<Income> findByPersonId(Integer personId, Pageable pageable);
	
}
