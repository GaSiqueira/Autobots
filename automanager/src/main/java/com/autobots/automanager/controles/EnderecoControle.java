package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.EnderecoRepositorio;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {
	@Autowired
	private ClienteRepositorio cliRepo;
	@Autowired
	private EnderecoRepositorio repositorio;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody Endereco endereco){
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente.getEndereco() != null) {
			return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
		}
		else if(cliente.getEndereco() == null) {
			cliente.setEndereco(endereco);
			cliRepo.save(cliente);
			repositorio.save(endereco);
			return new ResponseEntity<>(HttpStatus.CREATED);			
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/enderecos")
	public ResponseEntity<List<Endereco>> listar(){
		List<Endereco> enderecos = repositorio.findAll();
		if(!enderecos.isEmpty()) {
			return new ResponseEntity<List<Endereco>>(enderecos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Endereco>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Endereco atualizacao){
		Endereco endereco = repositorio.findById(atualizacao.getId()).orElse(null);
		if (endereco == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente != null) {
			atualizacao.setTitular(cliente.getNome());
			cliente.setEndereco(atualizacao);
			
			EnderecoAtualizador atualizador = new EnderecoAtualizador();
			atualizador.atualizar(endereco, atualizacao);
			
			cliRepo.save(cliente);
			repositorio.save(endereco);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
			
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> deletar(@RequestBody Endereco exclusao) {
		Endereco endereco = repositorio.findById(exclusao.getId()).orElse(null);
		if (endereco == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = cliRepo.findByNome(endereco.getTitular());
		if(cliente != null) {
			cliente.setEndereco(null);
			cliRepo.save(cliente);
			repositorio.delete(endereco);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	
}
