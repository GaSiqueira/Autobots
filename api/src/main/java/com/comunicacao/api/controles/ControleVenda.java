package com.comunicacao.api.controles;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.comunicacao.api.modelos.Venda;

@RestController
public class ControleVenda {
	@GetMapping("/todas-vendas-empresa/{id}")
	public ResponseEntity<?> obterVendas(@PathVariable Long id) {
		ResponseEntity<Venda[]> resposta = new RestTemplate()
				.getForEntity("http://localhost:8080/venda/vendaPempresa/{id}", Venda[].class, id);
		Venda[] vendaArray = resposta.getBody();
		if(vendaArray != null) {
			List<Venda> vendas= Arrays.asList(vendaArray);
			return new ResponseEntity<>(vendas, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
