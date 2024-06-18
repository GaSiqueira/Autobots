package com.autobots.automanager.controles;

import java.util.List;
import java.util.Set;

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

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControle {
	
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	@PostMapping("/cadastrar/{id}")
	public ResponseEntity<?> cadastrar(@RequestBody Mercadoria mercadoria, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		Empresa empresa = listagem.buscarEmpresa(usuario.getIdEmpresa());
		if (usuario != null && empresa != null) {
			cadastro.cadastrar(mercadoria);
			
			empresa.getMercadorias().add(mercadoria);
			usuario.getMercadorias().add(mercadoria);
			
			cadastro.cadastrar(usuario);
			cadastro.cadastrar(empresa);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Mercadoria>> listar(){
		List<Mercadoria> mercadorias = listagem.mercadorias();
		if(!mercadorias.isEmpty()) {
			return new ResponseEntity<List<Mercadoria>>(mercadorias ,HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Mercadoria>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Mercadoria> buscar(@PathVariable Long id){
		Mercadoria mercadoria = listagem.buscarMercadoria(id);
		if(mercadoria != null) {
			return new ResponseEntity<Mercadoria>(mercadoria ,HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Mercadoria>(HttpStatus.FOUND);
		}
	}
	
	@GetMapping("/mercadoriaPempresa/{id}")
	public ResponseEntity<Set<Mercadoria>> mercadoriaEmpresa (@PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			Set<Mercadoria> mercadorias = empresa.getMercadorias();
			return new ResponseEntity<Set<Mercadoria>>(mercadorias, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Set<Mercadoria>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Mercadoria mercadoria = listagem.buscarMercadoria(id);
		if(mercadoria != null) {
			removedor.deletar(mercadoria);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Mercadoria data){
		Mercadoria mercadoria = listagem.buscarMercadoria(data.getId());
		if(mercadoria != null) {
			atualizador.atualizar(mercadoria, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	}
