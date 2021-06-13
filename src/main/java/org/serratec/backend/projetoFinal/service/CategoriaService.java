package org.serratec.backend.projetoFinal.service;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.dto.CategoriaDto;
import org.serratec.backend.projetoFinal.entity.CategoriaEntity;
import org.serratec.backend.projetoFinal.mapper.CategoriaMapper;
import org.serratec.backend.projetoFinal.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepository;

	@Autowired
	CategoriaMapper categoriaMapper;
	
	@Autowired
	ProdutoService produtoService;
	
// metodo para listar tds as categorias
	public List<CategoriaEntity> getAll() {
		List<CategoriaEntity> dto = categoriaRepository.findAll();
		return dto;
	}
//metodo para pesquisar por id
	public CategoriaEntity getByNome(String nome) throws NotFoundException {
		Optional<CategoriaEntity> categoria = categoriaRepository.findByNome(nome);
		if (categoria.isEmpty()) {
			throw new NotFoundException("Não achei");
		}
		return categoria.get();
	}
//metodo para criar um n
	public CategoriaDto create(CategoriaDto categoria) {
		CategoriaEntity categoriaNova = categoriaMapper.toEntity(categoria);
		return categoriaMapper.toDto(categoriaRepository.save(categoriaNova));
	}
//meteodo para dar update em uma categoria
	public CategoriaDto update(String nome, CategoriaDto categoria) throws NotFoundException {
		CategoriaEntity categoriaEntity = this.getByNome(nome);
		if(categoria.getNome() != null) {
			categoriaEntity.setNome(categoria.getNome());
		}
		if(categoria.getDescricao() != null) {
			categoriaEntity.setDescricao(categoria.getDescricao());
		}
		return categoriaMapper.toDto(categoriaRepository.save(categoriaEntity));
	}
//metodo para deletar uma categoria
	public boolean delete(String nome) throws NotFoundException {
		boolean temproduto;
		Optional<CategoriaEntity> ctgo = categoriaRepository.findByNome(nome);
		CategoriaEntity ctgo2=ctgo.get();
		Long idctgo2 = ctgo2.getId();
		temproduto=this.CategoriaComProduto(idctgo2);
		
		if(temproduto==true) {
			return false;
		}else {
			categoriaRepository.deleteById(idctgo2);
			return true;
		}
		
	
	}
	

	public boolean CategoriaComProduto(Long idCategoria) throws NotFoundException{
		boolean temProduto = produtoService.produtoComCategoria(idCategoria);
		if(temProduto==true) {
			return true;
		}else {
			return false;
		}
		
	}
	
}
