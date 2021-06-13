package org.serratec.backend.projetoFinal.dto;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.br.CPF;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ClienteDto {
	
	private Long id;
	
	@NotNull
	@Size(min = 10, max = 150)
	private String email;
	
	@NotNull
	@Size(min = 10, max = 150)
	private String username;
	
	@NotNull
	@Size(min = 10, max = 150)
	private String senha;
	
	@NotNull
	@Size(min = 10, max = 150)
	private String nome;
	
	@JsonIgnore
	@NotNull
	//@Size(min = 10, max = 150)
	private String cpf;
	
	@NotNull
	@Size(min = 11, max = 15)
	private String telefone;
	
	@JsonIgnore
	@NotNull
	@Size(min = 10, max = 150)
	private LocalDate dataNascimento;
	@JsonIgnore
	private LocalDate dataAtualizacao;
	
	
	private String cep;
	
	private Integer numero;
	
	private String complemento;
	
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(LocalDate dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	
	

}
