package org.serratec.backend.projetoFinal.controller;

import java.util.List;

import org.serratec.backend.projetoFinal.dto.CategoriaDto;
import org.serratec.backend.projetoFinal.entity.CategoriaEntity;
import org.serratec.backend.projetoFinal.service.CategoriaService;
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
@RequestMapping("/categoria")
public class CategoriaController {
	
	@Autowired
	CategoriaService service;
	

	@GetMapping
	public List<CategoriaEntity> getAll() throws NotFoundException{
		return service.getAll();
	}
	
	@GetMapping("/{nome}")
	public CategoriaEntity getById(@PathVariable("nome") String nome) throws NotFoundException {
		return service.getByNome(nome);
	}
	
	@PostMapping
	public CategoriaDto create(@RequestBody CategoriaDto Categoria) {
		return service.create(Categoria);
	}
	
	@PutMapping("/{nome}")
	public CategoriaDto update(@PathVariable("nome") String nome, @RequestBody CategoriaDto Categoria) throws NotFoundException {
		return service.update(nome, Categoria);
	}
	
	@DeleteMapping("/{nome}")
	public String deleteNome(@PathVariable("nome") String nome) throws NotFoundException {
		Boolean retorneDelete = service.delete(nome);
		if(retorneDelete==false) {
			return "Não é possivel deletar categoria com produto";
		}else {
			return "Categoria deletada com sucesso";
		}
		
		
	}
	

}
