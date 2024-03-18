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

import rva.models.Dobavljac;
import rva.services.DobavljacService;

@RestController
public class DobavljacController {

	@Autowired
	private DobavljacService service;
	
	@GetMapping("/dobavljac")
	//Get maping je skracena verzija request maping anotacije
	//@RequestMapping(method = RequestMethod.GET, path = "/dobavljac")
	public List<Dobavljac> getAllDobavljacs(){
		return service.getAll();
	}
	
	@GetMapping("/dobavljac/id/{id}")
	public ResponseEntity<?> getDobavljacById(@PathVariable int id){
		Optional<Dobavljac> dobavljac = service.findById(id);
		if(dobavljac.isPresent()) {
			return ResponseEntity.ok(dobavljac.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
	}
	
	@GetMapping("/dobavljac/adresa/{adresa}")
	public ResponseEntity<?> getDobavljacsByAdresa(@PathVariable String adresa){
		List<Dobavljac> dobavljaci = service.getDobavljacsByAdresa(adresa);
		if(dobavljaci.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Adresa: " + adresa + " do not exist!");
		}
		return ResponseEntity.ok(dobavljaci);
	}
	
	@PostMapping("/dobavljac")
	public ResponseEntity<?> createDobavljac(@RequestBody Dobavljac dobavljac){
		if(service.existsById(dobavljac.getId())) {
			return ResponseEntity.status(409).body("Resource already exists!");
		}
		Dobavljac savedDobavljac = service.create(dobavljac);
		URI uri = URI.create("/dobavljac/id/" + savedDobavljac.getId());
		return ResponseEntity.created(uri).body(savedDobavljac);
	}
	
	@PutMapping("/dobavljac/id/{id}")
	public ResponseEntity<?> updateDobavljac(@RequestBody Dobavljac dobavljac, @PathVariable int id){
		Optional<Dobavljac> updatedDobavljac = service.update(dobavljac, id);
		if(updatedDobavljac.isPresent()) {
			return ResponseEntity.ok(updatedDobavljac.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" updated because it does not exist!");
	}
	
	@DeleteMapping("/dobavljac/id/{id}")
	public ResponseEntity<?> deleteDobavljac(@PathVariable int id ){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted!");
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" deleted because it does not exist!");
	}
}
