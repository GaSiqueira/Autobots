package com.comunicacao.api.controles;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.comunicacao.api.modelos.Servico;


@RestController
public class ControleServico {
	@GetMapping("/todos-servicos-empresa/{id}")
	public ResponseEntity<?> obterServicos(@PathVariable Long id) {
		ResponseEntity<Servico[]> resposta = new RestTemplate()
				.getForEntity("http://localhost:8080/servico/servicoPempresa/{id}", Servico[].class, id);
		Servico[] servicoArray = resposta.getBody();
		if(servicoArray != null) {
			List<Servico> servicos= Arrays.asList(servicoArray);
			return new ResponseEntity<>(servicos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
