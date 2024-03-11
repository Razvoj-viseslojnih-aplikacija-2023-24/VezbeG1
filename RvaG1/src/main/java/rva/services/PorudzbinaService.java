package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Dobavljac;
import rva.models.Porudzbina;

@Service
public interface PorudzbinaService extends CrudService<Porudzbina> {
	

	List<Porudzbina> getPorudzbinasByPlaceno(boolean placeno);
	
	List<Porudzbina> getByForeignKey(Dobavljac dobavljac);
}
