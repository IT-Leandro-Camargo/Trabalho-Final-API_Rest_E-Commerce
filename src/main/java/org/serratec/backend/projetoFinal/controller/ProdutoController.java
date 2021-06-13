package org.serratec.backend.projetoFinal.controller;

import java.util.List;

import org.serratec.backend.projetoFinal.dto.ProdutoDto;
import org.serratec.backend.projetoFinal.entity.ProdutoEntity;
import org.serratec.backend.projetoFinal.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javassist.NotFoundException;
import javassist.tools.web.BadHttpRequest;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
	@Autowired
	ProdutoService service;
	

	@GetMapping
	public List<ProdutoEntity> getAll() throws NotFoundException{
		return service.getAll();
	}
	
	@GetMapping("/{nome}")
	public ProdutoEntity getById(@PathVariable("nome") String nome) throws NotFoundException {
		return service.getByNome(nome);
	}
	
	@PostMapping
	public ProdutoDto create(@RequestBody ProdutoDto produto) throws NotFoundException {
		return service.create(produto);
	}
	
	@PutMapping("/{nome}")
	public ProdutoDto update(@PathVariable("nome") String nome, @RequestBody ProdutoDto produto) throws NotFoundException, BadHttpRequest {
		return service.update(nome, produto);
	}
	
	@DeleteMapping("/{nome}")
	public void deleteNome(@PathVariable("nome") String nome) throws NotFoundException {
		service.delete(nome);
	}
	
	@GetMapping("/verifica-estoque")
	public Boolean verificaEstoque(@RequestParam Long idProduto, @RequestParam Long qtde) {
		return service.verificaEstoque(idProduto, qtde);
	}
	

}
