package org.serratec.backend.projetoFinal.controller;

import java.util.List;

import org.serratec.backend.projetoFinal.dto.ClienteDto;
import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.serratec.backend.projetoFinal.service.ClienteService;
import org.serratec.backend.projetoFinal.service.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	ClienteService service;
	
	@GetMapping
	public List<ClienteEntity> getAll() throws NotFoundException{
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public ClienteEntity getById(@PathVariable("id") Long id) throws NotFoundException {
		return service.getById(id);
	}
	
//	@PostMapping("/login")
//	public ClienteEntity createLogin(@RequestBody ClienteEntity entity) {
//		
//		return service.createLogin(entity);
//	}
	
	
	@PostMapping
	public String create(@RequestBody ClienteDto cliente) throws NotFoundException {
		boolean retornoCadastro = service.create(cliente);
		
		if(retornoCadastro==true) {
			return "CPF já foi cadastrado";
		}else {
			return "Cliente cadastrado com sucesso";
		}
		
	}
	
	@PutMapping("/{id}")
	public ClienteDto update(@PathVariable("id") Long id, @RequestBody ClienteDto cliente) throws NotFoundException {
		return service.update(id, cliente);
	}
	
	@DeleteMapping("/{id}")
	public String deleteById(@PathVariable("id") Long id) throws NotFoundException {
		boolean clienteDeletado = service.delete(id);
		
		if(clienteDeletado==true) {
			return "Cliente deletado com sucesso!";
		}else {
			return "Não é possível delatar cliente que tanha pedido finalizado";
		}
	}
	
	
}
