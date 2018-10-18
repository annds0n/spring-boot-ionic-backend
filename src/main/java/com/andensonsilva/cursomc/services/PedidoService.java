package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Pedido;
import com.andensonsilva.cursomc.repositories.PedidoRepository;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    PedidoRepository repository;

    public Pedido buscar(Integer id) {

        Optional<Pedido> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Pedido não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

}
