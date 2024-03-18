package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Artikl;
import rva.repository.ArtiklRepository;
import rva.services.ArtiklService;

@Component
public class ArtiklServiceImpl implements ArtiklService {

	@Autowired
	private ArtiklRepository repo;
	
	@Override
	public List<Artikl> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}
	
	@Override
	public Optional<Artikl> findById(int id){
		return repo.findById(id);
	}

	@Override
	public Artikl create(Artikl t) {
		return repo.save(t);
	}

	@Override
	public Optional<Artikl> update(Artikl t, int id) {
		if(existsById(id)) {
			t.setId(id);
			return Optional.of(repo.save(t));
		}
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		repo.deleteById(id);

	}

	@Override
	public List<Artikl> getArtiklsByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

}
