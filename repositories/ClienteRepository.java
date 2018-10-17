package com.andensonsilva.cursomc.repositories;

import com.andensonsilva.cursomc.domain.Cidade;
import com.andensonsilva.cursomc.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
