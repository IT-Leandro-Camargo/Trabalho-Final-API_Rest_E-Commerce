package org.serratec.backend.projetoFinal.mapper;

import org.serratec.backend.projetoFinal.dto.ClienteDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {


	public ClienteEntity toEntity(ClienteDto dto) {
		ClienteEntity clienteNovo = new ClienteEntity();
		clienteNovo.setNome(dto.getNome());
		clienteNovo.setCpf(dto.getCpf());
		clienteNovo.setDataNascimento(dto.getDataNascimento());
		clienteNovo.setEmail(dto.getEmail());
		clienteNovo.setTelefone(dto.getTelefone());
		clienteNovo.setSenha(dto.getSenha());
		clienteNovo.setUsername(dto.getUsername());
		return clienteNovo;
		
	
	}

	public ClienteDto toDto(ClienteEntity cliente) {
		ClienteDto dto = new ClienteDto();
		dto.setNome(cliente.getNome());
		dto.setCpf(cliente.getCpf());
		dto.setDataNascimento(cliente.getDataNascimento());
		dto.setEmail(cliente.getEmail());
		dto.setTelefone(cliente.getTelefone());
		dto.setSenha(cliente.getSenha());
		dto.setUsername(cliente.getUsername());
		dto.setDataAtualizacao(cliente.getDataAtualizacao());
		
		return dto;
	}

}
