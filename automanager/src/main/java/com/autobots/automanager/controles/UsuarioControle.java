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

import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.enumeracoes.PerfilUsuario;
import com.autobots.automanager.servicos.ServicoAtualizador;
import com.autobots.automanager.servicos.ServicoCadastro;
import com.autobots.automanager.servicos.ServicoListagem;
import com.autobots.automanager.servicos.ServicoRemovedor;

@RestController
@RequestMapping("/usuario")
public class UsuarioControle {
	@Autowired
	private ServicoCadastro cadastro;
	@Autowired
	private ServicoListagem listagem;
	@Autowired
	private ServicoRemovedor removedor;
	@Autowired
	private ServicoAtualizador atualizador;
	
	
	@PostMapping("/cadastrar-fornecedor/{id}")
	public ResponseEntity<?> cadastrar (@RequestBody Usuario user, @PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			if(user.getMercadorias().size() > 0) {
				empresa.getMercadorias().addAll(user.getMercadorias());
			}
			
			user.setIdEmpresa(empresa.getId());
			user.setEmpresa(empresa.getNomeFantasia());
			cadastro.cadastrar(user);
			
			empresa.getUsuarios().add(user);
			cadastro.cadastrar(empresa);
			
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/cadastrar-cliente")
	public ResponseEntity<?> cadastrarCliente (@RequestBody Usuario cliente){
		cliente.getPerfis().add(PerfilUsuario.CLIENTE);
		cadastro.cadastrar(cliente);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@PostMapping("/cadastrar-funcionario/{id}")
	public ResponseEntity<?> cadastrarFuncionario (@RequestBody Usuario funcionario, @PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			funcionario.getPerfis().add(PerfilUsuario.FUNCIONARIO);
			funcionario.setIdEmpresa(empresa.getId());
			funcionario.setEmpresa(empresa.getNomeFantasia());
			cadastro.cadastrar(funcionario);
			empresa.getUsuarios().add(funcionario);
			cadastro.cadastrar(empresa);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> listar(){
		List<Usuario> usuarios = listagem.usuarios();
		if (!usuarios.isEmpty()) {
			return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<List<Usuario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/buscar/{id}")
	public ResponseEntity<Usuario> buscar(@PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if (usuario != null) {
			return new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
		}
	}
	@GetMapping("/usuariosPempresa/{id}")
	public ResponseEntity<Set<Usuario>> usuariosEmpresa(@PathVariable Long id){
		Empresa empresa = listagem.buscarEmpresa(id);
		if(empresa != null) {
			Set<Usuario> usuarios = empresa.getUsuarios();
			return new ResponseEntity<Set<Usuario>>(usuarios, HttpStatus.FOUND);
		}
		else {
			return new ResponseEntity<Set<Usuario>>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null && usuario.getIdEmpresa() != null) {
			Empresa empresa = listagem.buscarEmpresa(usuario.getIdEmpresa());
			empresa.getUsuarios().remove(usuario);
			removedor.deletar(usuario);
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/atualizar")
	public ResponseEntity<?> atualizar(@RequestBody Usuario data){
		Usuario usuario = listagem.buscarUsuario(data.getId());
		if(usuario != null) {
			atualizador.atualizar(usuario, data);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/cadastro-credencial/{id}")
	public ResponseEntity<?> cadastrarCredencial(@RequestBody CredencialUsuarioSenha credencial, @PathVariable Long id){
		Usuario usuario = listagem.buscarUsuario(id);
		if(usuario != null) {
			usuario.getCredenciais().add(credencial);
			cadastro.cadastrar(usuario);
			return new ResponseEntity<>(HttpStatus.CREATED);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
