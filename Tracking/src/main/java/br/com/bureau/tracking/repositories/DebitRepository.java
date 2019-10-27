package br.com.bureau.tracking.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.tracking.models.Debit;
import br.com.bureau.tracking.models.Person;

@Repository
public interface DebitRepository extends JpaRepository<Debit, Integer> {
	
	Debit findByIdAndPerson(Integer id, Person person);
	Page<Debit> findByPerson(Person person, Pageable pageable);

}
