package br.com.bureau.earnings.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bureau.earnings.models.Assets;

@Repository
public interface AssetsRepository extends JpaRepository<Assets, Integer> {

	Assets findByIdAndPersonId(Integer id, Integer personId);
	Page<Assets> findByPersonId(Integer personId, Pageable pageable);
}
