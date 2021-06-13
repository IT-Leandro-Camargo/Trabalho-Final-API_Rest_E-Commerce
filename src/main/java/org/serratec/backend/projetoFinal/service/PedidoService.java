package org.serratec.backend.projetoFinal.service;

import java.time.LocalDate;
import java.util.List;

import javax.mail.MessagingException;

import org.serratec.backend.projetoFinal.config.MailConfig;
import org.serratec.backend.projetoFinal.dto.PedidoDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.entity.PedidoEntity;
import org.serratec.backend.projetoFinal.entity.PedidoProdutoEntity;
import org.serratec.backend.projetoFinal.entity.ProdutoEntity;
import org.serratec.backend.projetoFinal.mapper.PedidoMapper;
import org.serratec.backend.projetoFinal.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class PedidoService {

	@Autowired
	PedidoRepository repository;

	@Autowired
	PedidoMapper mapper;

	@Autowired
	PedidoProdutoService pedidoProdutoService;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	MailConfig mailConfig;
	
	@Autowired
	ClienteService clienteService;
	
	

	public List<PedidoEntity> getAll() {
		return repository.findAll();
	}

	public PedidoEntity getById(Long id) {
		return repository.findById(id).get();
	}

	public PedidoDto create(PedidoDto pedido) throws NotFoundException {

		PedidoEntity pedidoEntity = repository.save(mapper.toEntity(pedido));
		pedidoEntity.setNumeroPedido(pedidoEntity.getId() * 1.0 + 1000.0);

		Long idPedido = pedidoEntity.getId();// compartilhar

		List<PedidoProdutoEntity> listaPedidoProduto = pedido.getPedidoProdutos();

		for (PedidoProdutoEntity pedidoProduto : listaPedidoProduto) {

			ProdutoEntity produto = pedidoProduto.getProduto();
			Long idProduto = produto.getId();
			Long qtdeProduto = pedidoProduto.getQtde();

			pedidoProdutoService.conteudoPedidoProduto(idPedido, idProduto, qtdeProduto);
			this.atualizaValorTotalPedido(idPedido);
		}

		return mapper.toDto(repository.save(pedidoEntity));

	}

	// Atualizar pedido, excluindo um produto no carrinho
	public String excluiProdutoPedido(Long idPedido, Long idProduto) throws NotFoundException {
		boolean aprovaAlteracao = this.statuspedido(idPedido);
		if (aprovaAlteracao == true) {

			Long qtde = (long) 0;
			pedidoProdutoService.verificaprodutoNoCarrinhoExclui(idPedido, idProduto, qtde);
			PedidoEntity pedidoEntity = repository.findById(idPedido).get();
			this.atualizaValorTotalPedido(idPedido);
			repository.save(pedidoEntity);
		}
		return "Pedido fechado não pode ser alterado";

	}

	// Atualiza o pedido, incluindo um produto no carrinho
	public String incluiProdutoPedido(Long idPedido, Long idProduto, Long qtde) throws NotFoundException {
		boolean aprovaAlteracao = this.statuspedido(idPedido);
		if (aprovaAlteracao == true) {

			PedidoEntity pedidoEntity = repository.findById(idPedido).get();
			pedidoProdutoService.verificaprodutoNoCarrinhoInclui(idPedido, idProduto, qtde);
			this.atualizaValorTotalPedido(idPedido);
			repository.save(pedidoEntity);
			return mapper.toDto(pedidoEntity) + "" + aprovaAlteracao;
		}
		return "Pedido fechado não pode ser alterado";
	}

	public void atualizaValorTotalPedido(Long idPedido) {

		Double valorTotal = pedidoProdutoService.atualizaValorTotalPedido(idPedido);
		PedidoEntity pedidoEntity = repository.findById(idPedido).get();
		pedidoEntity.setValorTotal(valorTotal);

	}

	// verifica se o pedido está com status "aberto", antes de fazer atualizações
	public boolean statuspedido(Long idPedido) {
		PedidoEntity pedidoEntity = repository.findById(idPedido).get();
		if ("aberto".equals(pedidoEntity.getStatus())) {
			return true;
		} else {
			return false;
		}
	}

	// fecha o pedido
	public PedidoDto fechaPedido(Long idPedido) throws MessagingException {
		PedidoEntity pedidoEntity = repository.findById(idPedido).get();
		boolean aprovaAlteracao = this.statuspedido(idPedido);
		if (aprovaAlteracao == true) {
			pedidoProdutoService.fechapedido(idPedido);
			this.atualizaValorTotalPedido(idPedido);
			pedidoEntity.setStatus("finalizado");
			pedidoEntity.setDataEntrega(LocalDate.now().plusDays(5));
			repository.save(pedidoEntity);
			pedidoProdutoService.excluiProdutoPedidoDoEstoque(idPedido);
			this.enviarEmail(pedidoEntity);
			return mapper.toDto(pedidoEntity);

		} else {
			return null;
		}

	}

	// exclui pedido aberto
	public boolean excluiPedidoAberto(Long idPedido) {
		PedidoEntity pedidoEntity = repository.findById(idPedido).get();
		boolean aprovaAlteracao = this.statuspedido(idPedido);

		if (aprovaAlteracao == true) {

			pedidoProdutoService.excluiPedidoProduto(idPedido);
			repository.deleteById(idPedido);
			return true;

		} else {
			return false;
		}

	}

	// envia email - será chamado quando o pedido for finalizado
	public String enviarEmail(PedidoEntity pedido) throws MessagingException {
		ClienteEntity clienteDoPedido = pedido.getClienteId();
		//String email = clientedoPedido.getEmail();
		String body = "<tr><td></td><td></td><td></td></tr>";
		List<PedidoProdutoEntity> listaPedidoProduto = pedido.getPedidoProdutos();
		Long qtde;
		Double preco;
		String nomeProduto;
		
		for (PedidoProdutoEntity pedidoProduto : listaPedidoProduto) {
			
			ProdutoEntity produto = pedidoProduto.getProduto();
			nomeProduto = produto.getNome();
			qtde = pedidoProduto.getQtde();
			preco = pedidoProduto.getPreco();
			body = body+"<tr><td>"+nomeProduto+"</td><td></td>"+"<td>"+qtde+"</td><td></td><td>"+preco+"</td></tr>";
			//listaTbody.add(body);
		}
		
		
		String subject = "Parabéns pela sua compra "+clienteDoPedido.getNome();
		String msg = "<b>Pedido: </b>"+pedido.getId()+"<br><b>Valor total :</b> "+pedido.getValorTotal()
		+"<br><b>Data da Entrega: </b>"+pedido.getDataEntrega()
		+"<table>"
		+ "<thead style=color:blue>"
		+ "<td><b>Produto</b></td><td></td>"
		+ "<td><b>Qtdes</b></td><td></td>"
		+"<td><b>Preço</b></td>"
		+"</thead>"
		+ "<tbody>"+body
		+"</tbody>"
		+ "</table>";
		
		return mailConfig.sendEmail(clienteDoPedido.getEmail(), subject, msg);

	}

}
