package org.serratec.backend.projetoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.entity.CategoriaEntity;
import org.serratec.backend.projetoFinal.entity.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long>{

	Optional<ProdutoEntity> findByNome(String nome);

	@Query("select categoriaId from ProdutoEntity")
	List<CategoriaEntity> listCategoriaComProduto(Long idCategoria);

}
