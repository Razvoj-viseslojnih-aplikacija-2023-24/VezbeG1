package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Dobavljac;
import rva.models.Porudzbina;

@Repository
public interface PorudzbinaRepository extends JpaRepository<Porudzbina, Integer>{

	List<Porudzbina> findByPlacenoEquals (boolean placeno);
	
	List<Porudzbina> findByDobavljac (Dobavljac dobavljac);
}
