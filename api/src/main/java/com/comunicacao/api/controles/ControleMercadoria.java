package com.comunicacao.api.controles;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Mercadoria;

@RestController
public class ControleMercadoria {
	@GetMapping("/todas-mercadorias-empresa/{id}")
	public ResponseEntity<?> obterMercadorias(@PathVariable Long id) {
		ResponseEntity<Mercadoria[]> resposta = new RestTemplate()
				.getForEntity("http://localhost:8080/mercadoria/mercadoriaPempresa/{id}", Mercadoria[].class, id);
		Mercadoria[] mercadoriaArray = resposta.getBody();
		if(mercadoriaArray != null) {
			List<Mercadoria> mercadorias = Arrays.asList(mercadoriaArray);
			return new ResponseEntity<>(mercadorias, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
