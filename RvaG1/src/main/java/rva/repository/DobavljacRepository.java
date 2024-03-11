package rva.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rva.models.Dobavljac;

@Repository
public interface DobavljacRepository extends JpaRepository<Dobavljac, Integer>{

	List<Dobavljac> findByAdresaContainingIgnoreCase(String adresa);
}
