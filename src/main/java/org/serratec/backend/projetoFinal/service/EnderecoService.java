package org.serratec.backend.projetoFinal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.dto.EnderecoDto;
import org.serratec.backend.projetoFinal.dto.ViaCepDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.serratec.backend.projetoFinal.mapper.EnderecoMapper;
import org.serratec.backend.projetoFinal.repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;

@Service
public class EnderecoService {
	@Autowired
	EnderecoRepository repository;
	
	@Autowired
	EnderecoMapper mapper;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	RestTemplate restTemplate;
	
	public List<EnderecoEntity> getAll(){
		return repository.findAll();
	}
	
	public List<EnderecoEntity> getById(Long id) throws NotFoundException {
		 Optional<List<EnderecoEntity>> endereco = repository.findByclienteIdId(id);
		
		if (endereco.isEmpty()) {
			throw new NotFoundException("Não achei");
		}
		
		return endereco.get();
		 
	}
	//tirei de dentro do parenteses: EnderecoDto endereco
	public EnderecoDto create(Long id,String cep,Integer numero, String complemento) throws NotFoundException {
		
		ViaCepDto enderecoCorreio = restTemplate.getForObject("https://viacep.com.br/ws/"+ cep + "/json", ViaCepDto.class);
		
		EnderecoEntity enderecoEntrega = new EnderecoEntity();
		EnderecoEntity enderecoCobranca = new EnderecoEntity();
		
		ClienteEntity cliente = clienteService.getById(id);
		
		
		enderecoEntrega.setCep(enderecoCorreio.getCep());
		enderecoEntrega.setRua(enderecoCorreio.getLogradouro());
		enderecoEntrega.setBairro(enderecoCorreio.getBairro());
		enderecoEntrega.setCidade(enderecoCorreio.getLocalidade());
		enderecoEntrega.setNumero(numero);
		enderecoEntrega.setComplemento(complemento);
		enderecoEntrega.setEstado(enderecoCorreio.getUf());
		enderecoEntrega.setTipoEndereco("entrega");
		enderecoEntrega.setClienteId(cliente);
		repository.save(enderecoEntrega);
		
		enderecoCobranca.setCep(enderecoCorreio.getCep());
		enderecoCobranca.setRua(enderecoCorreio.getLogradouro());
		enderecoCobranca.setBairro(enderecoCorreio.getBairro());
		enderecoCobranca.setCidade(enderecoCorreio.getLocalidade());
		enderecoCobranca.setNumero(numero);
		enderecoCobranca.setComplemento(complemento);
		enderecoCobranca.setEstado(enderecoCorreio.getUf());
		enderecoCobranca.setTipoEndereco("cobranca");
		enderecoCobranca.setClienteId(cliente);
		return  mapper.toDto(repository.save(enderecoCobranca));
		
	}
	
	public EnderecoDto update(Long id, EnderecoDto endereco) throws NotFoundException, BadHttpRequest {
		EnderecoEntity enderecoAtual = this.localizaEnderecoTipo(id,endereco.getTipoEndereco());
		
		if(endereco.getTipoEndereco()!=null) {
			if(endereco.getCep()!=null) {
				enderecoAtual.setCep(endereco.getCep());
			}
			
			if(endereco.getRua()!=null) {
				enderecoAtual.setRua(endereco.getRua());
			}
			
			if(endereco.getBairro()!=null) {
				enderecoAtual.setBairro(endereco.getBairro());
			}
			
			if(endereco.getCidade()!=null) {
				enderecoAtual.setCidade(endereco.getCidade());
			}
			
			if(endereco.getNumero()!=null) {
				enderecoAtual.setNumero(endereco.getNumero());
			}
			if(endereco.getComplemento()!=null) {
				enderecoAtual.setComplemento(endereco.getComplemento());
			}
			
			if(endereco.getEstado()!=null) {
				enderecoAtual.setEstado(endereco.getEstado());
			}
			
			ClienteEntity cliente = clienteService.getById(id);
			cliente.setDataAtualizacao(LocalDate.now());
			clienteService.atualizaCliente(cliente);
			
			
		}else {
			throw new BadHttpRequest();
		}
		
		return mapper.toDto(repository.save(enderecoAtual));
	}
	
	private EnderecoEntity localizaEnderecoTipo(Long id, String tipoEndereco) {
		// TODO Auto-generated method stub
		return repository.findByclienteIdIdAndTipoEndereco(id, tipoEndereco);
	}
	
	public void delete (Long id) {
		repository.deleteById(id);
	}

}
