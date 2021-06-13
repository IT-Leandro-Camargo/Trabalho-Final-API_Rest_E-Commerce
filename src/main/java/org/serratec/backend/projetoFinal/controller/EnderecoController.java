package org.serratec.backend.projetoFinal.controller;

import java.util.List;

import org.serratec.backend.projetoFinal.dto.EnderecoDto;
import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.serratec.backend.projetoFinal.service.EnderecoService;
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
import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping("/endereco")
public class EnderecoController {
	
	
	@Autowired
	EnderecoService service;
	
	@GetMapping
	public List<EnderecoEntity> getAll() throws NotFoundException{
		return service.getAll();
	}
	
	@GetMapping("/{id}")
	public List<EnderecoEntity> getById(@PathVariable("id") Long id) throws NotFoundException {
		return service.getById(id);
	}
	
//	@PostMapping("/{id}")
//	public EnderecoDto create(@PathVariable("id") Long id, @RequestBody EnderecoDto Endereco) throws NotFoundException {
//		return service.create(id,Endereco);
//	}
	
	@PutMapping("/{id}")
	public EnderecoDto update(@PathVariable("id") Long id, @RequestBody EnderecoDto endereco) throws NotFoundException, BadHttpRequest {
		return service.update(id, endereco);
	}
	
	@DeleteMapping("/{id}")
	public void deleteById(@PathVariable("id") Long id) throws NotFoundException {
		service.delete(id);
	}
	

}
