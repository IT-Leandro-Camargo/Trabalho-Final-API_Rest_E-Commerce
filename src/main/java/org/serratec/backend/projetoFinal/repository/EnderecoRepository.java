package org.serratec.backend.projetoFinal.repository;

import java.util.List;
import java.util.Optional;

import org.serratec.backend.projetoFinal.entity.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoEntity, Long>{

	EnderecoEntity findByclienteIdIdAndTipoEndereco(Long id, String tipoendereco);

	Optional <List<EnderecoEntity>> findByclienteIdId(Long id);

	
//	EnderecoEntity savaEnderecoEntity (Long id, EnderecoEntity endereco);

}
