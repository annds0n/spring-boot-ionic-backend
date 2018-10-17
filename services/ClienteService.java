package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.repositories.ClienteRepository;
import com.andensonsilva.cursomc.repositories.ClienteRepository;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository repository;

    public Cliente buscar(Integer id) {

        Optional<Cliente> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Cliente não encontrada. Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

}
