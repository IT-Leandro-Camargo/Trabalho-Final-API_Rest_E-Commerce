package org.serratec.backend.projetoFinal.mapper;

import org.serratec.backend.projetoFinal.dto.EnderecoDto;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

	public EnderecoEntity toEntity(EnderecoDto dto) {
		EnderecoEntity entity = new EnderecoEntity();

		entity.setCep(dto.getCep());
		entity.setRua(dto.getRua());
		entity.setBairro(dto.getBairro());
		entity.setNumero(dto.getNumero());
		entity.setComplemento(dto.getComplemento());
		entity.setEstado(dto.getEstado());
		entity.setTipoEndereco(dto.getTipoEndereco());
		entity.setCidade(dto.getCidade());
		return entity;
	}
	
	
	public EnderecoDto toDto (EnderecoEntity entity) {
		EnderecoDto dto = new EnderecoDto();
		
		dto.setCep(entity.getCep());
		dto.setRua(entity.getRua());
		dto.setBairro(entity.getBairro());
		dto.setNumero(entity.getNumero());
		dto.setComplemento(entity.getComplemento());
		dto.setEstado(entity.getEstado());
		dto.setTipoEndereco(entity.getTipoEndereco());
		dto.setCidade(entity.getCidade());
		
		
		return dto;
		
		
	}

}
