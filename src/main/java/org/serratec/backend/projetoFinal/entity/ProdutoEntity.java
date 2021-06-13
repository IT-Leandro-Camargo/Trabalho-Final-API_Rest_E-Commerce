package org.serratec.backend.projetoFinal.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProdutoEntity {

	@Id // O .IDENTITY GERA ID PASSANDO SUA CRIAÇÃO AUTOMATICAMENTE PELO BANCO DE DADOS
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nome;

	private String descricao;

	private Double preco;

	private Long qtdEstoque;

	private LocalDate dateCadastro;
	
	//inserir gat/set
	private LocalDate dateAtualizacao;
	
	//inserir gateter and setters
	//private FuncionarioEntity funcionarioAtualizacao;

	private Byte imagem;
	
	@OneToMany(mappedBy = "produto", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<PedidoProdutoEntity>pedidos = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", referencedColumnName ="id")
	@JsonBackReference//16:02
	private CategoriaEntity categoriaId;

	
	
	public LocalDate getDateAtualizacao() {
		return dateAtualizacao;
	}

	public void setDateAtualizacao(LocalDate dateAtualizacao) {
		this.dateAtualizacao = dateAtualizacao;
	}

	public List<PedidoProdutoEntity> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<PedidoProdutoEntity> pedidos) {
		this.pedidos = pedidos;
	}

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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public long getQtdEstoque() {
		return qtdEstoque;
	}

	public void setQtdEstoque(Long qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public LocalDate getDateCadastro() {
		return dateCadastro;
	}

	public void setDateCadastro(LocalDate dateCadastro) {
		this.dateCadastro = dateCadastro;
	}

	public Byte getImagem() {
		return imagem;
	}

	public void setImagem(Byte imagem) {
		this.imagem = imagem;
	}

	public List<PedidoProdutoEntity> getPedido() {
		return pedidos;
	}

	public void setPedido(List<PedidoProdutoEntity> pedido) {
		this.pedidos = pedido;
	}

	public CategoriaEntity getCategoriaId() {
		return categoriaId;
	}

	public void setCategoriaId(CategoriaEntity categoriaId) {
		this.categoriaId = categoriaId;
	}
	
	
}
