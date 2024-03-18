package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Artikl;
import rva.models.Porudzbina;
import rva.models.StavkaPorudzbine;
import rva.services.ArtiklService;
import rva.services.PorudzbinaService;
import rva.services.StavkaPorudzbineService;

@RestController
public class StavkaPorudzbineController {

	@Autowired
	private StavkaPorudzbineService service;
	
	@Autowired 
	private ArtiklService artiklService;
	
	@Autowired
	private PorudzbinaService porudzbinaService;
	
	
	@GetMapping("/stavkaPorudzbine")
	//Get maping je skracena verzija request maping anotacije
	//@RequestMapping(method = RequestMethod.GET, path = "/artikl")
	public List<StavkaPorudzbine> getAllStavkaPorudzbines(){
		return service.getAll();
	}
	
	@GetMapping("/stavkaPorudzbine/id/{id}")
	public ResponseEntity<?> getStavkaPorudzbineById(@PathVariable int id){
		Optional<StavkaPorudzbine> stavkaPorudzbine = service.findById(id);
		if(stavkaPorudzbine.isPresent()) {
			return ResponseEntity.ok(stavkaPorudzbine.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
	}
	
	@GetMapping("/stavkaPorudzbine/cena/{cena}")
	public ResponseEntity<?> getStavkaPorudzbinesByNaziv(@PathVariable double cena){
		List<StavkaPorudzbine> stavkaPorudzbinei = service.getStavkasByCenaLargerThan(cena);
		if(stavkaPorudzbinei.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Cena: " + cena + " do not exist!");
		}
		return ResponseEntity.ok(stavkaPorudzbinei);
	}
	
	@PostMapping("/stavkaPorudzbine")
	public ResponseEntity<?> createStavkaPorudzbine(@RequestBody StavkaPorudzbine stavkaPorudzbine){
		if(service.existsById(stavkaPorudzbine.getId())) {
			return ResponseEntity.status(409).body("Resource already exists!");
		}
		StavkaPorudzbine savedStavkaPorudzbine = service.create(stavkaPorudzbine);
		URI uri = URI.create("/stavkaPorudzbine/id/" + savedStavkaPorudzbine.getId());
		return ResponseEntity.created(uri).body(savedStavkaPorudzbine);
	}
	
	@PutMapping("/stavkaPorudzbine/id/{id}")
	public ResponseEntity<?> updateStavkaPorudzbine(@RequestBody StavkaPorudzbine stavkaPorudzbine, @PathVariable int id){
		Optional<StavkaPorudzbine> updatedStavkaPorudzbine = service.update(stavkaPorudzbine, id);
		if(updatedStavkaPorudzbine.isPresent()) {
			return ResponseEntity.ok(updatedStavkaPorudzbine.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" updated because it does not exist!");
	}
	
	@DeleteMapping("/stavkaPorudzbine/id/{id}")
	public ResponseEntity<?> deleteStavkaPorudzbine(@PathVariable int id ){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted!");
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" deleted because it does not exist!");
	}
	
	@GetMapping("/stavka-porudzbine/artikl/{foreignKey}")
	public ResponseEntity<?> getStavkeByArtikl(@PathVariable int foreignKey){
		Optional<Artikl> artikl = artiklService.findById(foreignKey);
		if(artikl.isPresent()) {
			List<StavkaPorudzbine> stavke = service.getByForeignKey(artikl.get());
			if(stavke.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " + foreignKey
						+ " do not exist!");
			}else {
				return ResponseEntity.ok(stavke);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key!");
	}
	
	@GetMapping("/stavka-porudzbine/porudzbina/{foreignKey}")
	public ResponseEntity<?> getStavkeByPorudzbina(@PathVariable int foreignKey){
		Optional<Porudzbina> porudzbina = porudzbinaService.findById(foreignKey);
		if(porudzbina.isPresent()) {
			List<StavkaPorudzbine> stavke = service.getByForeignKey(porudzbina.get());
			if(stavke.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " + foreignKey
						+ " do not exist!");
			}else {
				return ResponseEntity.ok(stavke);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key!");
	}
}
