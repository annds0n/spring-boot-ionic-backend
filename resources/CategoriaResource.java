package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
