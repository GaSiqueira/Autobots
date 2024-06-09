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
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private DocumentoRepositorio repositorio;
	@Autowired
	private ClienteRepositorio cliRepo;
	
	@PostMapping("/cadastrar")
	public ResponseEntity<?> cadastrar(@RequestBody Documento documento) {
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if(cliente.getNome().equals(documento.getTitular())){
			cliente.getDocumentos().add(documento);
			repositorio.save(documento);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		}
	}
	
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> listar(){
		List<Documento> documentos = repositorio.findAll();
		if(!documentos.isEmpty()) {
			return new ResponseEntity<List<Documento>>(documentos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Documento>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/excluir")
	public ResponseEntity<?> deletar(@RequestBody Documento exclusao) {
		Documento documento = repositorio.findById(exclusao.getId()).orElse(null);
		if (documento == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente != null) {
			boolean removido = cliente.getDocumentos().removeIf(doc -> doc.getId().equals(documento.getId()));
			if(removido) {
				cliRepo.save(cliente);
				repositorio.delete(documento);
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Documento atualizacao) {
		Documento documento = repositorio.findById(atualizacao.getId()).orElse(null);
		if (documento == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Cliente cliente = cliRepo.findByNome(documento.getTitular());
		if(cliente != null) {
			boolean remover = cliente.getDocumentos().removeIf(doc -> doc.getId().equals(documento.getId()));
			if(remover) {
				atualizacao.setTitular(cliente.getNome());
				cliente.getDocumentos().add(atualizacao);
				
				DocumentoAtualizador atualizador = new DocumentoAtualizador();
				atualizador.atualizar(documento, atualizacao);
				
				cliRepo.save(cliente);
				repositorio.save(documento);
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
