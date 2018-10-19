package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Pedido;
import com.andensonsilva.cursomc.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
