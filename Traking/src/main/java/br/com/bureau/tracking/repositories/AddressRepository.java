package br.com.bureau.tracking.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.tracking.models.Address;
import br.com.bureau.tracking.models.Person;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	Page<Address> findByPerson(Person person, Pageable pageable);
	
	Address findByIdAndPerson(Integer id, Person person);

}
