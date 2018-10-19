package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.dto.CategoriaDTO;
import com.andensonsilva.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaService service;


    @GetMapping()
    public ResponseEntity<List<CategoriaDTO>> findAll() {

        List<Categoria> categorias = this.service.listar();

        List<CategoriaDTO> categoriaDTOs = categorias.stream().map(c -> new CategoriaDTO(c)).collect(Collectors.toList());

        return ResponseEntity.ok().body(categoriaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Categoria c, @PathVariable Integer id){
        c.setId(id);

        c = this.service.atualizar(c);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.remover(id);

        return ResponseEntity.noContent().build();
    }

}
