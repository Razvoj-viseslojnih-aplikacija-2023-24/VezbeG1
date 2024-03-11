package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Artikl;

@Repository
public interface ArtiklRepository extends JpaRepository<Artikl, Integer> {

	List<Artikl> findByNazivContainingIgnoreCase (String naziv);
	
}
