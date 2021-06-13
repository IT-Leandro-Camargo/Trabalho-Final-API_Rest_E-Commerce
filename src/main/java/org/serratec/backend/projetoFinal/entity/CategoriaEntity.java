package org.serratec.backend.projetoFinal.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CategoriaEntity {

	@Id // O .IDENTITY GERA ID PASSANDO SUA CRIAÇÃO AUTOMATICAMENTE PELO BANCO DE DADOS
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	
	private Long id;

	private String nome;

	private String descricao;
	
	@JsonIgnore
	@OneToMany(mappedBy = "categoriaId")
	private List<ProdutoEntity> produtos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<ProdutoEntity> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<ProdutoEntity> produtos) {
		this.produtos = produtos;
	}

	
}
