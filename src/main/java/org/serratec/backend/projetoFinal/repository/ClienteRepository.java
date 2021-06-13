package org.serratec.backend.projetoFinal.repository;

import org.serratec.backend.projetoFinal.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{

	ClienteEntity findAllByCpf(String cpfNovo);

	
	ClienteEntity findByNome(String username);


	ClienteEntity findByUsername(String username);

	



}
