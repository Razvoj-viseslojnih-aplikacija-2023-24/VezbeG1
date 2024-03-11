package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

@Repository
public interface StavkaPorudzbineRepository extends JpaRepository<StavkaPorudzbine, Integer> {

	List<StavkaPorudzbine> findByCenaGreaterThanOrderByCena(double cena);
	
	List<StavkaPorudzbine> findByArtikl(Artikl artikl);
	
	List<StavkaPorudzbine> findByPorudzbina(Porudzbina porudzbina);
}
