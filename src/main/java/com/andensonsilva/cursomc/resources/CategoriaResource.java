package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;


    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {

        Categoria categoria = this.service.buscar(id);

        return ResponseEntity.ok().body(categoria);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody Categoria c) {
        c = service.inserir(c);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                          .path("/{id}").buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

}
