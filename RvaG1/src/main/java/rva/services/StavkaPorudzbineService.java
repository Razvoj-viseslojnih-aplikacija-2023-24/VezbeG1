package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;

@Service
public interface StavkaPorudzbineService extends CrudService<StavkaPorudzbine> {

	List<StavkaPorudzbine> getStavkasByCenaLargerThan(double cena);
	
	List<StavkaPorudzbine> getByForeignKey(Artikl artikl);
	List<StavkaPorudzbine> getByForeignKey(Porudzbina porudzbina);
}
