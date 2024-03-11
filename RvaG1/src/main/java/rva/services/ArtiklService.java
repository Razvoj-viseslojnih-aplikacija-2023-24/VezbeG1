package rva.services;

import java.util.List;

import org.springframework.stereotype.Service;

import rva.models.Artikl;

@Service
public interface ArtiklService extends CrudService<Artikl> {

	List<Artikl> getArtiklsByNaziv(String naziv);
}
