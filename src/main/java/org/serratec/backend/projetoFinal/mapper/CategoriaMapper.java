package org.serratec.backend.projetoFinal.mapper;

import org.serratec.backend.projetoFinal.dto.CategoriaDto;
import org.serratec.backend.projetoFinal.entity.CategoriaEntity;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
	
	public CategoriaEntity toEntity(CategoriaDto dto) {
		CategoriaEntity entity = new CategoriaEntity();
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		return entity;
	}
	
	public CategoriaDto toDto(CategoriaEntity categoria) {
		CategoriaDto dto = new CategoriaDto();
		dto.setNome(categoria.getNome());
		dto.setDescricao(categoria.getDescricao());
		return dto;
	}
	
	
}
