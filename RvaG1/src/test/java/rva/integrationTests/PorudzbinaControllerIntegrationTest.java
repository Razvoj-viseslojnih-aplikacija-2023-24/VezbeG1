package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import rva.models.Porudzbina;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PorudzbinaControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template;

	long largestId;

	public void createHighestId() {
		ResponseEntity<List<Porudzbina>> response = template.exchange("/porudzbina", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Porudzbina>>() {
				});
		ArrayList<Porudzbina> list = (ArrayList<Porudzbina>) response.getBody();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			if (largestId <= list.get(i).getId()) {
				largestId = list.get(i).getId() + 1;
			}
		}

	}

	private void getHighestId() {
		createHighestId();
		largestId--;
	}

	@Test
	@Order(1)
	void testGetAllPorudzbinas() {
		ResponseEntity<List<Porudzbina>> response = template.exchange("/porudzbina", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Porudzbina>>() {
				});
		int statusCode = response.getStatusCode().value();
		List<Porudzbina> porudzbinas = response.getBody();

		assertEquals(200, statusCode);
		assertNotNull(porudzbinas);
	}

	@Test
	@Order(2)
	void testFindPorudzbinaById() {
		long id = 1;
		ResponseEntity<Porudzbina> response = template.getForEntity("/porudzbina/" + id, Porudzbina.class);
		int statusCode = response.getStatusCode().value();
		Porudzbina porudzbina = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(porudzbina);
		assertEquals(id, porudzbina.getId());
	}

	@Test
	@Order(3)
	void testFindPorudzbinaByIznosGreaterThan() {
		double iznos = 50;
		ResponseEntity<List<Porudzbina>> response = template.exchange("/porudzbina/iznos/" + iznos, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Porudzbina>>(){});
		int statusCode = response.getStatusCode().value();
		List<Porudzbina> porudzbinas =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(porudzbinas.get(0));
		for(Porudzbina p: porudzbinas) {
			assertTrue(p.getIznos() > iznos);
		}
	}

	@Test
	@Order(4)
	void testGetPorudzbineByDobavljac() {
		long dobavljacId = 1;
		ResponseEntity<List<Porudzbina>> response = template.exchange("/porudzbina/dobavljac/" + dobavljacId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Porudzbina>>(){});
		int statusCode = response.getStatusCode().value();
		List<Porudzbina> porudzbinas =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(porudzbinas.get(0));
		for(Porudzbina p: porudzbinas) {
			assertTrue(p.getDobavljac().getId() == 1);
		}
	}

	@Test
	@Order(5)
	void testCreatePorudzbina() {
		Porudzbina porudzbina = new Porudzbina();
		Date datum = new Date();
		porudzbina.setDatum(datum);
		Date isporuceno = new Date();
		porudzbina.setIsporuceno(isporuceno);
		porudzbina.setIznos(600);
		porudzbina.setPlaceno(false);
		
		HttpEntity<Porudzbina> entity = new HttpEntity<Porudzbina>(porudzbina);
		createHighestId();
		ResponseEntity<Porudzbina> response  = template.postForEntity("/porudzbina", entity, Porudzbina.class);		
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(201, response.getStatusCode().value());
		assertEquals(datum, response.getBody().getDatum());
		assertEquals(isporuceno, response.getBody().getIsporuceno());
		assertEquals(600, response.getBody().getIznos());
		assertFalse(response.getBody().isPlaceno());
	}

	@Test
	@Order(6)
	void testUpdatePorudzbina() {
		Porudzbina porudzbina = new Porudzbina();
		porudzbina.setIznos(1000);
		porudzbina.setPlaceno(true);
		
		HttpEntity<Porudzbina> entity = new HttpEntity<Porudzbina>(porudzbina);
		getHighestId();
		ResponseEntity<Porudzbina> response  = template.exchange("/porudzbina/" + largestId, HttpMethod.PUT, entity, Porudzbina.class);
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(200, response.getStatusCode().value());
		assertEquals(1000, response.getBody().getIznos());
		assertTrue(response.getBody().isPlaceno());
	}

	@Test
	@Order(7)
	void testDeletePorudzbina() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/porudzbina/" + largestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("has been deleted!"));
	}

}