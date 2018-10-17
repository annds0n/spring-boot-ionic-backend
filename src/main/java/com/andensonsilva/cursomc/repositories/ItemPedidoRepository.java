package com.andensonsilva.cursomc.repositories;

import com.andensonsilva.cursomc.domain.Cidade;
import com.andensonsilva.cursomc.domain.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
}
