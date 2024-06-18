package com.comunicacao.api.modelos;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Empresa {
	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("razaoSocial")
	private String razaoSocial;
	
	@JsonProperty("nomeFantasia")
	private String nomeFantasia;
	
	@JsonProperty("telefones")
	private List<Telefone> telefones;
	
	@JsonProperty("endereco")
	private Endereco endereco;
	
	@JsonProperty("cadastro")
	private Date cadastro;
	
	@JsonProperty("usuarios")
	private List<Usuario> usuarios;
	
	@JsonProperty("mercadorias")
	private List<Mercadoria> mercadorias;
	
	@JsonProperty("servicos")
	private List<Servico> servicos;
	
	@JsonProperty("vendas")
	private List<Venda> vendas;
}
