package org.serratec.backend.projetoFinal.service;

import java.util.List;

import org.serratec.backend.projetoFinal.entity.PedidoEntity;
import org.serratec.backend.projetoFinal.entity.PedidoProdutoEntity;
import org.serratec.backend.projetoFinal.entity.ProdutoEntity;
import org.serratec.backend.projetoFinal.repository.PedidoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class PedidoProdutoService {

	@Autowired
	PedidoProdutoRepository repository;

	@Autowired
	ProdutoService produtoService;

	@Autowired
	PedidoService pedidoService;

	public PedidoProdutoEntity create(PedidoProdutoEntity pedidoProduto) {
		return repository.save(pedidoProduto);

	}

	public void conteudoPedidoProduto(Long idPedido, Long idProduto, Long qtdeProduto) throws NotFoundException {
		// verifica se a quantidade em estoque é suficiente
		String status = this.atualizaStatusPedidoProduto(idPedido, idProduto, qtdeProduto);
		Double acrescimo;

		// cria um objeto PedidoProdutoEntity
		PedidoProdutoEntity pedidoProduto = new PedidoProdutoEntity();

		// seta os atibutos do objeto
		ProdutoEntity produto = produtoService.getById(idProduto);
		pedidoProduto.setProduto(produto);

		PedidoEntity pedido = pedidoService.getById(idPedido);
		pedidoProduto.setPedido(pedido);

		pedidoProduto.setQtde(qtdeProduto);

		pedidoProduto.setPreco(produto.getPreco());

		pedidoProduto.setNomeproduto(produto.getNome());

		pedidoProduto.setIdProduto(produto.getId());
		pedidoProduto.setStatus(status);

		this.create(pedidoProduto);

	}

	public void verificaprodutoNoCarrinhoExclui(Long idPedido, Long idProduto, Long qtde) throws NotFoundException {
		PedidoProdutoEntity pedidoproduto = repository.findAllByPedido_idAndProduto_id(idPedido, idProduto);
		if (pedidoproduto != null) {
			this.excluiprodutoPedido(idPedido, idProduto);
		}
	}

	public void excluiprodutoPedido(Long idPedido, Long idProduto) throws NotFoundException {

		PedidoProdutoEntity pedidoproduto = repository.findAllByPedido_idAndProduto_id(idPedido, idProduto);
		Long idPedidoProduto = pedidoproduto.getId();
		this.excluiProduto(idPedidoProduto);

		// return (-1.0 * qtde * preco);

	}

	public void excluiProduto(Long id) {
		repository.deleteById(id);

	}

	public void verificaprodutoNoCarrinhoInclui(Long idPedido, Long idProduto, Long qtde) throws NotFoundException {
		PedidoProdutoEntity pedidoPoduto = repository.findAllByPedido_idAndProduto_id(idPedido, idProduto);
		String status = this.atualizaStatusPedidoProduto(idPedido, idProduto, qtde);
		if (pedidoPoduto != null) {

			pedidoPoduto.setQtde(qtde);
			pedidoPoduto.setStatus(status);
			this.create(pedidoPoduto);

		} else {

			this.incluiProdutoPedido(idPedido, idProduto, qtde);
		}

	}

	public void incluiProdutoPedido(Long idPedido, Long idProduto, Long qtde) throws NotFoundException {

		String status = this.atualizaStatusPedidoProduto(idPedido, idProduto, qtde);
		Double acrescimo;
		PedidoProdutoEntity pedidoProduto = new PedidoProdutoEntity();

		// seta os atibutos do objeto
		ProdutoEntity produto = produtoService.getById(idProduto);
		pedidoProduto.setProduto(produto);

		PedidoEntity pedido = pedidoService.getById(idPedido);
		pedidoProduto.setPedido(pedido);

		pedidoProduto.setQtde(qtde);
		pedidoProduto.setPreco(produto.getPreco());
		pedidoProduto.setNomeproduto(produto.getNome());
		pedidoProduto.setIdProduto(produto.getId());
		pedidoProduto.setStatus(status);
		this.create(pedidoProduto);
	}

	public boolean verificaEstoque(Long idProduto, Long qtde) {
		return produtoService.verificaEstoque(idProduto, qtde);

	}

	public String atualizaStatusPedidoProduto(Long idPedido, Long idProduto, Long qtde) {
		boolean verifica = this.verificaEstoque(idProduto, qtde);
		String status;
		if (verifica == true) {
			status = "Produto no carinho";
		} else {

			status = "Estoque insuficiente";
		}

		return status;

	}

	public Double atualizaValorTotalPedido(Long pedido_id) {
		List<PedidoProdutoEntity> listapedidoPoduto = repository.findAllByPedido_id(pedido_id);
		Double valorTotalPedido = 0., valorTotalProduto;
//		
//		
		for (PedidoProdutoEntity pedidoProduto : listapedidoPoduto) {
//			status=(toString().getStatus()+"");
			if ("Produto no carinho".equals(pedidoProduto.getStatus())) {

				valorTotalProduto = pedidoProduto.getPreco() * pedidoProduto.getQtde();
				valorTotalPedido = valorTotalPedido + valorTotalProduto;
				//status = pedidoProduto.getStatus();

			}
		}

		return valorTotalPedido;

	}

 //fecha o pedido
	public void fechapedido(Long pedido_id) {
		this.limpaCarrinho(pedido_id);
	}
	
	//limpa pedido
	public void limpaCarrinho(Long pedido_id) {
		List<PedidoProdutoEntity> listapedidoPoduto = repository.findAllByPedido_id(pedido_id);
		
		for (PedidoProdutoEntity pedidoProduto : listapedidoPoduto) {
			if("Estoque insuficiente".equals(pedidoProduto.getStatus())) {
				repository.deleteById(pedidoProduto.getId());
			}
		}
		
	}
	
	public void excluiProdutoPedidoDoEstoque (Long pedido_id) {
		
		List<PedidoProdutoEntity> listapedidoPoduto = repository.findAllByPedido_id(pedido_id);
		Long idProduto;
		Long qtde;
		
		for (PedidoProdutoEntity pedidoProduto : listapedidoPoduto) {
			idProduto = pedidoProduto.getIdProduto();
			qtde = pedidoProduto.getQtde();
			produtoService.excluiProdutoPedidoDoEstoque(idProduto, qtde);
			
		}
		
	}
	
	public void excluiPedidoProduto(Long pedido_id) {
		List<PedidoProdutoEntity> listapedidoPoduto = repository.findAllByPedido_id(pedido_id);
		Long idPedidoProduto;
		
		
		for (PedidoProdutoEntity pedidoProduto : listapedidoPoduto) {
			idPedidoProduto = pedidoProduto.getId();
			repository.deleteById(pedidoProduto.getId());
			
		}
		
		
		
	}
	
}

/*
 * public PedidoProdutoEntity create(PedidoProdutoEntity pedidoProduto) { return
 * repository.save(pedidoProduto);
 * 
 * }
 * 
 * public Double conteudoPedidoProduto(Long idPedido, Long idProduto, Long
 * qtdeProduto) throws NotFoundException { //verifica se a quantidade em estoque
 * é suficiente String status = this.atualizaStatusPedidoProduto(idPedido,
 * idProduto, qtdeProduto); Double acrescimo;
 * 
 * // cria um objeto PedidoProdutoEntity PedidoProdutoEntity pedidoProduto = new
 * PedidoProdutoEntity();
 * 
 * // seta os atibutos do objeto ProdutoEntity produto =
 * produtoService.getById(idProduto); pedidoProduto.setProduto(produto);
 * 
 * PedidoEntity pedido = pedidoService.getById(idPedido);
 * pedidoProduto.setPedido(pedido);
 * 
 * pedidoProduto.setQtde(qtdeProduto);
 * 
 * pedidoProduto.setPreco(produto.getPreco());
 * 
 * pedidoProduto.setNomeproduto(produto.getNome());
 * 
 * pedidoProduto.setIdProduto(produto.getId()); pedidoProduto.setStatus(status);
 * 
 * this.create(pedidoProduto);
 * 
 * if (status == "ok") { acrescimo = 1.0 * qtdeProduto * produto.getPreco(); }
 * else { acrescimo = 0.; }
 * 
 * 
 * 
 * // // salva o objeto PedidoProdutoEntity no banco // PedidoProdutoEntity
 * pedidoProdutoSalvo = this.create(pedidoProduto); // // // recupera a qtde e o
 * preço do produto salvo dentro no banco // Long qtde =
 * pedidoProdutoSalvo.getQtde(); // Double preco =
 * pedidoProdutoSalvo.getPreco(); // // // retorna o valor total do
 * pedidoProduto return acrescimo;
 * 
 * }
 * 
 * public Double verificaprodutoNoCarrinhoExclui(Long idPedido, Long idProduto,
 * Long qtde) throws NotFoundException { PedidoProdutoEntity pedidoproduto =
 * repository.findAllByPedido_idAndProduto_id(idPedido, idProduto); Double
 * abatimento; if (pedidoproduto != null) { abatimento =
 * this.excluiprodutoPedido(idPedido, idProduto); } else { abatimento = 0.; }
 * 
 * return abatimento; }
 * 
 * public Double excluiprodutoPedido(Long idPedido, Long idProduto) throws
 * NotFoundException {
 * 
 * PedidoProdutoEntity pedidoproduto =
 * repository.findAllByPedido_idAndProduto_id(idPedido, idProduto);
 * 
 * Long idPedidoProduto = pedidoproduto.getId(); Long qtde =
 * pedidoproduto.getQtde(); Double preco = pedidoproduto.getPreco();
 * 
 * this.excluiProduto(idPedidoProduto); return (-1.0 * qtde * preco);
 * 
 * }
 * 
 * public void excluiProduto(Long id) {
 * 
 * repository.deleteById(id);
 * 
 * }
 * 
 * public Double verificaprodutoNoCarrinhoInclui(Long idPedido, Long idProduto,
 * Long qtde) throws NotFoundException { Double abatimento;
 * 
 * abatimento = this.verificaprodutoNoCarrinhoExclui(idPedido, idProduto, qtde);
 * 
 * 
 * return (this.incluiProdutoPedido(idPedido, idProduto, qtde) + abatimento);
 * 
 * }
 * 
 * public Double incluiProdutoPedido(Long idPedido, Long idProduto, Long qtde)
 * throws NotFoundException {
 * 
 * String status = this.atualizaStatusPedidoProduto(idPedido, idProduto, qtde);
 * Double acrescimo; PedidoProdutoEntity pedidoProduto = new
 * PedidoProdutoEntity();
 * 
 * // seta os atibutos do objeto ProdutoEntity produto =
 * produtoService.getById(idProduto); pedidoProduto.setProduto(produto);
 * 
 * PedidoEntity pedido = pedidoService.getById(idPedido);
 * pedidoProduto.setPedido(pedido);
 * 
 * pedidoProduto.setQtde(qtde); pedidoProduto.setPreco(produto.getPreco());
 * pedidoProduto.setNomeproduto(produto.getNome());
 * pedidoProduto.setIdProduto(produto.getId()); pedidoProduto.setStatus(status);
 * this.create(pedidoProduto);
 * 
 * if (status == "ok") { acrescimo = 1.0 * qtde * produto.getPreco();
 * 
 * } else { acrescimo = 0.; }
 * 
 * 
 * return acrescimo; }
 * 
 * public boolean verificaEstoque(Long idProduto, Long qtde) { return
 * produtoService.verificaEstoque(idProduto, qtde);
 * 
 * }
 * 
 * public String atualizaStatusPedidoProduto(Long idPedido, Long idProduto, Long
 * qtde) { boolean verifica = this.verificaEstoque(idProduto, qtde); String
 * status; if (verifica == true) { status = "ok"; } else {
 * 
 * status = "Estoque insuficiente"; }
 * 
 * return status;
 * 
 * }
 * 
 * }
 * 
 * 
 */
