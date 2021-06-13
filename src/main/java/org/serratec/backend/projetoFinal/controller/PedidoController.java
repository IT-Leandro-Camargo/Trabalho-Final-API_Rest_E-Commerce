package org.serratec.backend.projetoFinal.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.serratec.backend.projetoFinal.dto.PedidoDto;
import org.serratec.backend.projetoFinal.entity.PedidoEntity;
import org.serratec.backend.projetoFinal.mapper.PedidoMapper;
import org.serratec.backend.projetoFinal.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
	
	@Autowired
	PedidoService service;
	
	@Autowired
	PedidoMapper mapper;
	
	@GetMapping
	public List<PedidoEntity> getAll(){
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public PedidoEntity getById( @PathVariable("id") Long id) {
		return service.getById(id);
	}
	
	@PostMapping()
	public PedidoDto create(@RequestBody PedidoDto pedido) throws NotFoundException {
		return service.create(pedido);
	}

	@PutMapping("/exclui-produto")
	public void excluiProduto(@RequestParam(name="pedidoId") Long idPedido,@RequestParam(name="produtoId") Long idProduto) throws NotFoundException {
		service.excluiProdutoPedido(idPedido, idProduto);
		
	}
	
	@PutMapping("/inclui-produto")
	public void incluiProdutoPedido(@RequestParam(name="pedidoId") Long idPedido,@RequestParam(name="produtoId") Long idProduto,@RequestParam Long qtde) throws NotFoundException {
		 service.incluiProdutoPedido(idPedido, idProduto, qtde);
	}
	
//	@GetMapping("status/{id}")
//	public boolean getStatus( @PathVariable("id") Long id) {
//		return service.statuspedido(id);
//	}
	
	@PutMapping("/fecha-pedido")
	public PedidoDto fechaPedido(@RequestParam(name="pedidoId") Long idPedido) throws MessagingException {
		return service.fechaPedido(idPedido);
	}
	
	@DeleteMapping("/deleta-pedido")
	public String deletePadido(@RequestParam(name="pedidoId") Long idPedido) {
		boolean deletado = service.excluiPedidoAberto(idPedido);
		
		if(deletado==true) {
			return "Pedido deletado com sucesso";
		}else {
			return "Não é possível deletar um pedido finalizado!";
		}
		
	}
	
}
