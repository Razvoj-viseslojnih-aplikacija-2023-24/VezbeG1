package rva;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestServer {

	@GetMapping("/zbir")
	public double zbirBrojeva() {
		double prvi = Math.random()*10;
		double drugi = Math.random()*10;
		return prvi + drugi;
	}
	
}
