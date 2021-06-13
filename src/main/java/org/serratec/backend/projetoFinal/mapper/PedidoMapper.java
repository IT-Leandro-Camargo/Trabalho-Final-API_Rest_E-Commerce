package org.serratec.backend.projetoFinal.mapper;

import org.serratec.backend.projetoFinal.dto.PedidoDto;
import org.serratec.backend.projetoFinal.dto.PedidoProdutoDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.entity.PedidoEntity;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

	public PedidoEntity toEntity(PedidoDto dto) {
		PedidoEntity entity = new PedidoEntity();
		entity.setDataPedido(dto.getDataPedido());
		entity.setDataEntrega(dto.getDataEntrega());
		entity.setClienteId(dto.getClienteId());
		entity.setStatus(dto.getStatus());
		entity.setNumeroPedido(dto.getNumeroPedido());
		entity.setPedidoProdutos(dto.getPedidoProdutos());
		entity.setValorTotal(dto.getValorTotal());
//		entity.setCombo(dto.getCombo());
		entity.setValorTotal(dto.getValorTotal());
//		entity.setProdutos(dto.getProdutos());
		return entity;
	}
	public PedidoDto toDto(PedidoEntity entity) {
		PedidoDto dto = new PedidoDto();
		ClienteEntity cliEntity = entity.getClienteId();
//		cliDto.setNome(cliEntity.getNome());
//		cliDto.setEmail(cliEntity.getEmail());
//		cliDto.setTelefone(cliEntity.getTelefone());
	
		dto.setDataPedido(entity.getDataPedido());
		dto.setDataEntrega(entity.getDataEntrega());
		
	//	dto.setClienteId(entity.getClienteId());
		
		dto.setNomeCliente(cliEntity.getNome());
		dto.setEmailCliente(cliEntity.getEmail());
		dto.setTelefoneCliente(cliEntity.getTelefone());
		
		dto.setStatus(entity.getStatus());
		dto.setValorTotal(entity.getValorTotal());
		dto.setNumeroPedido(entity.getNumeroPedido());
		dto.setPedidoProdutos(entity.getPedidoProdutos());
		
		return dto;
	}
}
