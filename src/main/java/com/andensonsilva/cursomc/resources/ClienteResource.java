package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {

    @Autowired
    private ClienteService service;


    @GetMapping("/{id}")
    public ResponseEntity<Cliente> findById(@PathVariable Integer id) {

        Cliente Cliente = this.service.buscar(id);

        return ResponseEntity.ok().body(Cliente);
    }

}
