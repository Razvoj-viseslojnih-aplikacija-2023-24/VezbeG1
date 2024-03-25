package rva.integrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
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

import rva.models.StavkaPorudzbine;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StavkaPorudzbineControllerIntegrationTest {
	
	@Autowired
	TestRestTemplate template;

	long largestId;

	public void createHighestId() {
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavka-porudzbine", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>() {
				});
		ArrayList<StavkaPorudzbine> list = (ArrayList<StavkaPorudzbine>) response.getBody();
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
	void testGetAllStavkaPorudzbine() {
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavka-porudzbine", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>() {
				});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkePorudzbine = response.getBody();

		assertEquals(200, statusCode);
		assertNotNull(stavkePorudzbine);
	}

	@Test
	@Order(2)
	void testFindStavkaPorudzbineById() {
		long id = 1;
		ResponseEntity<StavkaPorudzbine> response = template.getForEntity("/stavka-porudzbine/" + id, StavkaPorudzbine.class);
		int statusCode = response.getStatusCode().value();
		StavkaPorudzbine stavkaPorudzbine = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(stavkaPorudzbine);
		assertEquals(id, stavkaPorudzbine.getId());
	}

	@Test
	@Order(3)
	void testFindStavkaPorudzbineByCenaLessThan() {
		double cena = 400;
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavka-porudzbine/cena/" + cena, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>(){});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkePorudzbine =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(stavkePorudzbine.get(0));
		for(StavkaPorudzbine p: stavkePorudzbine) {
			assertTrue(p.getCena() < cena);
		}
	}

	@Test
	@Order(4)
	void testGetStavkePorudzbineByPorudzbina() {
		long porudzbinaId = 1;
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavka-porudzbine/porudzbina/" + porudzbinaId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>(){});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkePorudzbine =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(stavkePorudzbine.get(0));
		for(StavkaPorudzbine p: stavkePorudzbine) {
			assertTrue(p.getPorudzbina().getId() == 1);
		}
	}
	
	@Test
	@Order(5)
	void testGetStavkePorudzbineByArtikl() {
		long artiklId = 1;
		ResponseEntity<List<StavkaPorudzbine>> response = template.exchange("/stavka-porudzbine/artikl/" + artiklId, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<StavkaPorudzbine>>(){});
		int statusCode = response.getStatusCode().value();
		List<StavkaPorudzbine> stavkePorudzbine =  response.getBody();
		
		assertEquals(200, statusCode );
		assertNotNull(stavkePorudzbine.get(0));
		for(StavkaPorudzbine p: stavkePorudzbine) {
			assertTrue(p.getArtikl().getId() == 1);
		}
	}

	@Test
	@Order(6)
	void testCreateStavkaPorudzbine() {
		StavkaPorudzbine stavkaPorudzbine = new StavkaPorudzbine();
		stavkaPorudzbine.setCena(1000);
		stavkaPorudzbine.setJedinicaMere("kg");
		stavkaPorudzbine.setKolicina(5);
		stavkaPorudzbine.setRedniBroj(11);
		
		HttpEntity<StavkaPorudzbine> entity = new HttpEntity<StavkaPorudzbine>(stavkaPorudzbine);
		createHighestId();
		ResponseEntity<StavkaPorudzbine> response  = template.postForEntity("/stavka-porudzbine", entity, StavkaPorudzbine.class);		
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(201, response.getStatusCode().value());
		assertEquals(stavkaPorudzbine.getCena(), response.getBody().getCena());
		assertEquals(stavkaPorudzbine.getJedinicaMere(), response.getBody().getJedinicaMere());
		assertEquals(stavkaPorudzbine.getKolicina(), response.getBody().getKolicina());
		assertEquals(stavkaPorudzbine.getRedniBroj(), response.getBody().getRedniBroj());
	}

	@Test
	@Order(7)
	void testUpdateStavkaPorudzbine() {
		StavkaPorudzbine stavkaPorudzbine = new StavkaPorudzbine();
		stavkaPorudzbine.setCena(2000);
		stavkaPorudzbine.setJedinicaMere("kg izmenjeno");
		stavkaPorudzbine.setKolicina(20);
		stavkaPorudzbine.setRedniBroj(20);
		
		HttpEntity<StavkaPorudzbine> entity = new HttpEntity<StavkaPorudzbine>(stavkaPorudzbine);
		getHighestId();
		ResponseEntity<StavkaPorudzbine> response  = template.exchange("/stavka-porudzbine/" + largestId, HttpMethod.PUT, entity, StavkaPorudzbine.class);
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(200, response.getStatusCode().value());
		assertEquals(stavkaPorudzbine.getCena(), response.getBody().getCena());
		assertEquals(stavkaPorudzbine.getJedinicaMere(), response.getBody().getJedinicaMere());
		assertEquals(stavkaPorudzbine.getKolicina(), response.getBody().getKolicina());
		assertEquals(stavkaPorudzbine.getRedniBroj(), response.getBody().getRedniBroj());
	}

	@Test
	@Order(8)
	void testDeleteStavkaPorudzbine() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/stavka-porudzbine/" + largestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("has been deleted!"));
	}

}
