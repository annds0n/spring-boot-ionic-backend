package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Pedido;
import com.andensonsilva.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pedidos")
public class PedidoResource {

    @Autowired
    private PedidoService service;


    @GetMapping("/{id}")
    public ResponseEntity<Pedido> findById(@PathVariable Integer id) {

        Pedido Pedido = this.service.buscar(id);

        return ResponseEntity.ok().body(Pedido);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido pedido) {
        pedido = this.service.inserir(pedido);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                          .path("/{id}").buildAndExpand(pedido.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

}
