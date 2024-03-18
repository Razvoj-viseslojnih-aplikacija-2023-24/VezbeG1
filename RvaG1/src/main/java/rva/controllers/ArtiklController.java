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
import rva.services.ArtiklService;

@RestController
public class ArtiklController {

	@Autowired
	private ArtiklService service;
	
	@GetMapping("/artikl")
	//Get maping je skracena verzija request maping anotacije
	//@RequestMapping(method = RequestMethod.GET, path = "/artikl")
	public List<Artikl> getAllArtikls(){
		return service.getAll();
	}
	
	@GetMapping("/artikl/id/{id}")
	public ResponseEntity<?> getArtiklById(@PathVariable int id){
		Optional<Artikl> artikl = service.findById(id);
		if(artikl.isPresent()) {
			return ResponseEntity.ok(artikl.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
	}
	
	@GetMapping("/artikl/naziv/{naziv}")
	public ResponseEntity<?> getArtiklsByNaziv(@PathVariable String naziv){
		List<Artikl> artikli = service.getArtiklsByNaziv(naziv);
		if(artikli.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Naziv: " + naziv + " do not exist!");
		}
		return ResponseEntity.ok(artikli);
	}
	
	@PostMapping("/artikl")
	public ResponseEntity<?> createArtikl(@RequestBody Artikl artikl){
		if(service.existsById(artikl.getId())) {
			return ResponseEntity.status(409).body("Resource already exists!");
		}
		Artikl savedArtikl = service.create(artikl);
		URI uri = URI.create("/artikl/id/" + savedArtikl.getId());
		return ResponseEntity.created(uri).body(savedArtikl);
	}
	
	@PutMapping("/artikl/id/{id}")
	public ResponseEntity<?> updateArtikl(@RequestBody Artikl artikl, @PathVariable int id){
		Optional<Artikl> updatedArtikl = service.update(artikl, id);
		if(updatedArtikl.isPresent()) {
			return ResponseEntity.ok(updatedArtikl.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" updated because it does not exist!");
	}
	
	@DeleteMapping("/artikl/id/{id}")
	public ResponseEntity<?> deleteArtikl(@PathVariable int id ){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted!");
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" deleted because it does not exist!");
	}
	
	
	
	
}
