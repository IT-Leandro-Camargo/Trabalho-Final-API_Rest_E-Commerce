package org.serratec.backend.projetoFinal.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double numeroPedido;

	//@JsonBackReference//16:08
	@OneToMany(mappedBy = "pedido")
	private List<PedidoProdutoEntity> pedidoProdutos = new ArrayList<>();

	private Double valorTotal;

	private LocalDate dataPedido;

	private LocalDate dataEntrega;

	private String status;
	
	//@JsonBackReference//16:02
	@ManyToOne()
	@JoinColumn(name = "cliente_id", referencedColumnName = "id")
	private ClienteEntity clienteId;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	
	public Double getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Double numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public List<PedidoProdutoEntity> getPedidoProdutos() {
		return pedidoProdutos;
	}

	public void setPedidoProdutos(List<PedidoProdutoEntity> pedidoProdutos) {
		this.pedidoProdutos = pedidoProdutos;
	}

	public Double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public LocalDate getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(LocalDate dataPedido) {
		this.dataPedido = dataPedido;
	}

	public LocalDate getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(LocalDate dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ClienteEntity getClienteId() {
		return clienteId;
	}

	public void setClienteId(ClienteEntity clienteId) {
		this.clienteId = clienteId;
	}

}