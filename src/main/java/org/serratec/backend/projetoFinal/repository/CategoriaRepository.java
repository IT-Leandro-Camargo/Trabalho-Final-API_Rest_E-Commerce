package org.serratec.backend.projetoFinal.repository;

import java.util.Optional;

import org.serratec.backend.projetoFinal.entity.CategoriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<CategoriaEntity, Long>{

	Optional<CategoriaEntity> findByNome(String nome);

	void deleteByNome(String nome);


}
