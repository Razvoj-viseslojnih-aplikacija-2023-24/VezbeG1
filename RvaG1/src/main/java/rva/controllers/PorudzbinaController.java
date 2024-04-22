package rva.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import rva.models.Dobavljac;
import rva.models.Porudzbina;
import rva.services.DobavljacService;
import rva.services.PorudzbinaService;

@CrossOrigin
@RestController
public class PorudzbinaController {

	@Autowired
	private PorudzbinaService service;
	
	@Autowired
	private DobavljacService dobavljacService;
	
	@GetMapping("/porudzbina")
	//Get maping je skracena verzija request maping anotacije
	//@RequestMapping(method = RequestMethod.GET, path = "/porudzbina")
	public List<Porudzbina> getAllPorudzbinas(){
		return service.getAll();
	}
	
	@GetMapping("/porudzbina/id/{id}")
	public ResponseEntity<?> getPorudzbinaById(@PathVariable int id){
		Optional<Porudzbina> porudzbina = service.findById(id);
		if(porudzbina.isPresent()) {
			return ResponseEntity.ok(porudzbina.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " does not exist!");
	}
	
	@GetMapping("/porudzbina/naziv/{placeno}")
	public ResponseEntity<?> getPorudzbinasByNaziv(@PathVariable boolean placeno){
		List<Porudzbina> porudzbinai = service.getPorudzbinasByPlaceno(placeno);
		if(porudzbinai.isEmpty()) {
			return ResponseEntity.status(404).body("Resources with Placeno: " + placeno + " do not exist!");
		}
		return ResponseEntity.ok(porudzbinai);
	}
	
	@PostMapping("/porudzbina")
	public ResponseEntity<?> createPorudzbina(@RequestBody Porudzbina porudzbina){
		if(service.existsById(porudzbina.getId())) {
			return ResponseEntity.status(409).body("Resource already exists!");
		}
		Porudzbina savedPorudzbina = service.create(porudzbina);
		URI uri = URI.create("/porudzbina/id/" + savedPorudzbina.getId());
		return ResponseEntity.created(uri).body(savedPorudzbina);
	}
	
	@PutMapping("/porudzbina/id/{id}")
	public ResponseEntity<?> updatePorudzbina(@RequestBody Porudzbina porudzbina, @PathVariable int id){
		Optional<Porudzbina> updatedPorudzbina = service.update(porudzbina, id);
		if(updatedPorudzbina.isPresent()) {
			return ResponseEntity.ok(updatedPorudzbina.get());
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" updated because it does not exist!");
	}
	
	@DeleteMapping("/porudzbina/id/{id}")
	public ResponseEntity<?> deletePorudzbina(@PathVariable int id ){
		if(service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok("Resource with ID: " + id + " has been deleted!");
		}
		return ResponseEntity.status(404).body("Resource with requested ID: " + id + " could not be" + 
				" deleted because it does not exist!");
	}
	
	@GetMapping("/porudzbin/dobavljac/{foreignKey}")
	public ResponseEntity<?> getPorudzbineByDobavljac(@PathVariable int foreignKey){
		Optional<Dobavljac> dobavljac = dobavljacService.findById(foreignKey);
		if(dobavljac.isPresent()) {
			List<Porudzbina> porudzbine = service.getByForeignKey(dobavljac.get());
			if(porudzbine.isEmpty()) {
				return ResponseEntity.status(404).body("Resources with foreign key: " + foreignKey
						+ " do not exist!");
			}else {
				return ResponseEntity.ok(porudzbine);
			}
		}
		return ResponseEntity.status(400).body("Invalid foreign key!");
	}
}
