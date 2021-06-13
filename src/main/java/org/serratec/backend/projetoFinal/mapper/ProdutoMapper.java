package org.serratec.backend.projetoFinal.mapper;

import org.serratec.backend.projetoFinal.dto.ProdutoDto;
import org.serratec.backend.projetoFinal.entity.ProdutoEntity;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
	
	
	public ProdutoEntity toEntity (ProdutoDto dto) {
		ProdutoEntity entity = new ProdutoEntity();
		
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setPreco(dto.getPreco());
		entity.setQtdEstoque(dto.getQtdEstoque());
	//	entity.setDate(dto.getDateCadastro());
		
		return entity;
		
	}
	
	public ProdutoDto toDto (ProdutoEntity entity) {
		ProdutoDto dto = new ProdutoDto();
		
		dto.setNome(entity.getNome());
		dto.setDescricao(entity.getDescricao());
		dto.setPreco(entity.getPreco());
		dto.setQtdEstoque(entity.getQtdEstoque());
		dto.setDateCadastro(entity.getDateCadastro());
		dto.setDateAtualizacao(entity.getDateAtualizacao());
		
		return dto;
		
	}
	
	
}
