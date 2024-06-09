package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Documento documento, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null) {
			usuario.getDocumentos().add(documento);
			cadastro.cadastrar(usuario);
			cadastro.cadastrar(documento);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Documento>> listar(){
		List<Documento> documentos = listagem.documentos();
		if(!documentos.isEmpty()) {
			return new ResponseEntity<List<Documento>>(documentos, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Documento>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Documento> buscar(@PathVariable Long id){
		Documento documento = listagem.buscarDocumento(id);
		if(documento != null) {
			return new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Documento>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Documento documento = listagem.buscarDocumento(id);
		if(documento != null) {
			removedor.deletar(documento);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Documento data){
		Documento documento = listagem.buscarDocumento(data.getId());
		if(documento != null) {
			atualizador.atualizar(documento, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
