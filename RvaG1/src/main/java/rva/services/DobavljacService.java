package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Dobavljac;

@Service
public interface DobavljacService extends CrudService<Dobavljac> {

	List<Dobavljac> getDobavljacsByAdresa(String adresa);
}
