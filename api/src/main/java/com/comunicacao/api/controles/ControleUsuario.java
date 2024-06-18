package com.comunicacao.api.controles;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import com.comunicacao.api.modelos.Usuario;

@RestController
public class ControleUsuario {
	@GetMapping("/todos-usuarios-empresa/{id}")
	public ResponseEntity<?> obterUsuarios(@PathVariable Long id) {
		ResponseEntity<Usuario[]> resposta = new RestTemplate()
				.getForEntity("http://localhost:8080/usuario/usuariosPempresa/{id}", Usuario[].class, id);
		Usuario[] usuarioArray = resposta.getBody();
		if(usuarioArray != null) {
			List<Usuario> usuarios = Arrays.asList(usuarioArray);
			return new ResponseEntity<>(usuarios, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}