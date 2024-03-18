package rva.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rva.models.Artikl;
import rva.models.Dobavljac;
import rva.repository.DobavljacRepository;
import rva.services.DobavljacService;

@Component
public class DobavljacServiceImpl implements DobavljacService {

	@Autowired
	private DobavljacRepository repo;
	
	@Override
	public List<Dobavljac> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}
	
	@Override
	public Optional<Dobavljac> findById(int id){
		return repo.findById(id);
	}

	@Override
	public Dobavljac create(Dobavljac t) {
		return repo.save(t);
	}

	@Override
	public Optional<Dobavljac> update(Dobavljac t, int id) {
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
	public List<Dobavljac> getDobavljacsByAdresa(String adresa) {
		return repo.findByAdresaContainingIgnoreCase(adresa);
	}

}
