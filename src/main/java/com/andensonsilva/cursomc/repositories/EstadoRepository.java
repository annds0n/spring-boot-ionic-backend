package com.andensonsilva.cursomc.repositories;

import com.andensonsilva.cursomc.domain.Cidade;
import com.andensonsilva.cursomc.domain.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
}
