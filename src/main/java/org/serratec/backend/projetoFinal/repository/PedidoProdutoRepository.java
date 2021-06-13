package org.serratec.backend.projetoFinal.repository;

import java.util.List;

import org.serratec.backend.projetoFinal.entity.PedidoProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoProdutoRepository extends JpaRepository<PedidoProdutoEntity, Long>{

	PedidoProdutoEntity findAllByPedido_idAndProduto_id(Long idPedido, Long idProduto);

	List<PedidoProdutoEntity> findAllByPedido_id(Long pedido_id);

}
