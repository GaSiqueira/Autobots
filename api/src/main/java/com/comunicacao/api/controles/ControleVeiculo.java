package com.comunicacao.api.controles;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Veiculo;

@RestController
public class ControleVeiculo {
	@GetMapping("/todos-veiculos")
	public ResponseEntity<?> obterVeiculos() {
		ResponseEntity<Veiculo[]> resposta = new RestTemplate()
				.getForEntity("http://localhost:8080/veiculo/listar", Veiculo[].class);
		Veiculo[] veiculoArray = resposta.getBody();
		if(veiculoArray != null) {
			List<Veiculo> veiculos = Arrays.asList(veiculoArray);
			return new ResponseEntity<>(veiculos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
