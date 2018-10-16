package com.andensonsilva.cursomc.repositories;

import com.andensonsilva.cursomc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
}
