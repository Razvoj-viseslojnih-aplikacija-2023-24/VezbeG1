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

import rva.models.Dobavljac;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DobavljacControllerIntegrationTest {

	@Autowired
	TestRestTemplate template;

	long largestId;

	public void createHighestId() {
		ResponseEntity<List<Dobavljac>> response = template.exchange("/dobavljac", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Dobavljac>>() {
				});
		ArrayList<Dobavljac> list = (ArrayList<Dobavljac>) response.getBody();
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
	void testGetAllDobavljacs() {
		ResponseEntity<List<Dobavljac>> response = template.exchange("/dobavljac", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Dobavljac>>() {
				});
		int statusCode = response.getStatusCode().value();
		List<Dobavljac> dobavljacs = response.getBody();

		assertEquals(200, statusCode);
		assertNotNull(dobavljacs);
	}

	@Test
	@Order(2)
	void testGetDobavljacById() {
		long id = 1;
		ResponseEntity<Dobavljac> response = template.getForEntity("/dobavljac/" + id, Dobavljac.class);
		int statusCode = response.getStatusCode().value();
		Dobavljac dobavljac = response.getBody();
		
		assertEquals(200, statusCode);
		assertNotNull(dobavljac);
		assertEquals(id, dobavljac.getId());
	}

	@Test
	@Order(3)
	void testGetDobavljacsByNaziv() {
		String naziv = "AD";
		ResponseEntity<List<Dobavljac>> response = template.exchange("/dobavljac/naziv/" + naziv, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Dobavljac>>(){});
		int statusCode = response.getStatusCode().value();
		List<Dobavljac> dobavljacs =  response.getBody();
		String nazivDobavljaca =   dobavljacs.get(0).getNaziv();
		
		assertEquals(200, statusCode );
		assertNotNull(dobavljacs.get(0));
		assertTrue(nazivDobavljaca.startsWith(naziv));	
	}

	@Test
	@Order(4)
	void testCreateDobavljac() {
		Dobavljac dobavljac = new Dobavljac();
		dobavljac.setNaziv("JUNIT test naziv");
		dobavljac.setAdresa("JUNIT test adresa");
		dobavljac.setKontakt("JUnit test contact");
		
		HttpEntity<Dobavljac> entity = new HttpEntity<Dobavljac>(dobavljac);
		createHighestId();
		ResponseEntity<Dobavljac> response  = template.postForEntity("/dobavljac", entity, Dobavljac.class);		
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(201, response.getStatusCode().value());
		assertEquals(dobavljac.getNaziv(), response.getBody().getNaziv());
		assertEquals(dobavljac.getKontakt(), response.getBody().getKontakt());
		assertEquals(dobavljac.getAdresa(), response.getBody().getAdresa());
	}

	@Test
	@Order(5)
	void testUpdateDobavljac() {
		Dobavljac dobavljac = new Dobavljac();
		dobavljac.setNaziv("Izmenjeni naziv");
		dobavljac.setAdresa("Izmenjena adresa");
		dobavljac.setKontakt("Izmenjeni contact");
		
		HttpEntity<Dobavljac> entity = new HttpEntity<Dobavljac>(dobavljac);
		getHighestId();
		ResponseEntity<Dobavljac> response  = template.exchange("/dobavljac/" + largestId, HttpMethod.PUT, entity, Dobavljac.class);
		
		assertTrue(response.hasBody());
		assertEquals(largestId, response.getBody().getId());
		assertEquals(200, response.getStatusCode().value());
		assertEquals(dobavljac.getNaziv(), response.getBody().getNaziv());
		assertEquals(dobavljac.getKontakt(), response.getBody().getKontakt());
		assertEquals(dobavljac.getAdresa(), response.getBody().getAdresa());
	}

	@Test
	@Order(6)
	void testDeleteDobavljac() {
		getHighestId();
		ResponseEntity<String> response = template.exchange("/dobavljac/" + largestId, HttpMethod.DELETE, null, String.class);
		
		assertEquals(200, response.getStatusCode().value());
		assertTrue(response.getBody().contains("has been deleted!"));
	}
}
