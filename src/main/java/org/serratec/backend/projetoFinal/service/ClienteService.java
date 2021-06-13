package org.serratec.backend.projetoFinal.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.dto.ClienteDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.serratec.backend.projetoFinal.entity.PedidoEntity;
import org.serratec.backend.projetoFinal.mapper.ClienteMapper;
import org.serratec.backend.projetoFinal.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class ClienteService {

	@Autowired
	ClienteRepository repository;
	
	@Autowired
	EnderecoService enderecoService;
	
	@Autowired
	ClienteMapper mapper;

	@Autowired
	BCryptPasswordEncoder bCrypt;
	
	
	
	public List<ClienteEntity> getAll() throws NotFoundException {
		return repository.findAll();
	}

	public ClienteEntity getById(Long id) throws NotFoundException {
		Optional<ClienteEntity> cliente = repository.findById(id);
		if (cliente.isEmpty()) {
			throw new NotFoundException("Não achei");
		}
		return cliente.get();
	}

	public Boolean create(ClienteDto cliente) throws NotFoundException {
		ClienteEntity clienteNovo = mapper.toEntity(cliente);
		
		String cpfNovo= clienteNovo.getCpf();
		boolean cpfEncontrado = this.verificaCpfNovo(cpfNovo);
		
		if(cpfEncontrado==true) {
			return cpfEncontrado;
		}else {
			clienteNovo.setDataCadastro(LocalDate.now());
			clienteNovo.setSenha(bCrypt.encode(clienteNovo.getSenha()));
			ClienteEntity clienteSalvo = repository.save(clienteNovo);
			
			//inclui 12/06/21 21:52
			Long idClienteSalvo = clienteSalvo.getId();
			String cepClienteSalvo = cliente.getCep();
			Integer numeroClienteSalvo = cliente.getNumero();
			String complementoClienteSalvo = cliente.getComplemento();
			enderecoService.create(idClienteSalvo, cepClienteSalvo, numeroClienteSalvo, complementoClienteSalvo);
			//this.createLogin(clienteSalvo);
			
			return cpfEncontrado;
		}
		
	}

	public ClienteDto update(Long id, ClienteDto cliente) throws NotFoundException {
		ClienteEntity clienteEntity = this.getById(id);

		if (cliente.getNome() != null) {
			clienteEntity.setNome(cliente.getNome());
		}

		if (cliente.getEmail() != null) {
			clienteEntity.setEmail(cliente.getEmail());
		}

		if (cliente.getUsername() != null) {
			clienteEntity.setUsername(cliente.getUsername());
		}

		if (cliente.getSenha() != null) {
			clienteEntity.setSenha(cliente.getSenha());
		}

		if (cliente.getTelefone() != null) {
			clienteEntity.setTelefone(cliente.getTelefone());
		}

		if (cliente.getDataNascimento() != null) {
			clienteEntity.setDataNascimento(cliente.getDataNascimento());
		}

		
		clienteEntity.setDataAtualizacao(LocalDate.now());
		return mapper.toDto(repository.save(clienteEntity));
	}

	
	public boolean delete(Long id) throws NotFoundException {
		boolean retornoConsulta = this.clienteComPedido(id);
		
		if(retornoConsulta==true) {
			return false;
		}else {
			List<EnderecoEntity> listaEnderecosDoCliente = enderecoService.getById(id);
			Long idEnderecoDoCliente;
			for (EnderecoEntity enderecoDoCliente : listaEnderecosDoCliente) {
				idEnderecoDoCliente = enderecoDoCliente.getId();
				enderecoService.delete(idEnderecoDoCliente);
			}
			repository.deleteById(id);
			return true;
		}
		
	}
	
	//Verifica se o cpf já foi cadastrado
	public boolean verificaCpfNovo(String cpfNovo) {
		
		ClienteEntity clienteNovo = repository.findAllByCpf(cpfNovo);
		if(clienteNovo!=null){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean clienteComPedido(Long id) {
		ClienteEntity clienteComPedidoFinalizado = repository.findById(id).get();
		List<PedidoEntity> listaPedidoFinalizado = clienteComPedidoFinalizado.getPedidos();
		Boolean consulta=false;
		for (PedidoEntity pedido : listaPedidoFinalizado) {
			String statusPedido = pedido.getStatus();
			if("finalizado".equals(statusPedido)) {
				consulta = true;
				break;
			}
		}
		return consulta;
		
	}
	
	public void atualizaCliente(ClienteEntity cliente) {
		repository.save(cliente);
	}

	
	public ClienteEntity createLogin(ClienteEntity cliente) {
		cliente.setSenha(bCrypt.encode(cliente.getSenha()));
//		ClienteEntity clienteNovo = mapper.toDto(cliente);
		
		//cliente.setDataCadastro(LocalDate.now());
		
		return repository.save(cliente);
	}
	
}

