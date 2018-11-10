package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.dto.CategoriaDTO;
import com.andensonsilva.cursomc.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.validation.Valid;
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

    /*
    * Colocar o tamanho de página 24 por padrão
    * */
    @GetMapping("/page")
    public ResponseEntity<Page<CategoriaDTO>> findPage(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "2") Integer size,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String order
    ) {

        Page<Categoria> categorias = this.service.encontrarPagina(page, size, orderBy, order);

        Page<CategoriaDTO> categoriaDTOs = categorias.map(c -> new CategoriaDTO(c));

        return ResponseEntity.ok().body(categoriaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer id) {

        Categoria categoria = this.service.buscar(id);

        return ResponseEntity.ok().body(categoria);
    }

    /**
     *
     * Autoriza apenas usuários com permissões específicas, no caso ADMIN
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Void> insert(@Valid  @RequestBody CategoriaDTO c) {
        Categoria categoria = this.service.converterDTOparaCategoria(c);
        categoria = service.inserir(categoria);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                          .path("/{id}").buildAndExpand(c.getId()).toUri();

        return ResponseEntity.created(uri).build();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO categoriaDTO, @PathVariable Integer id){

        Categoria categoria = this.service.converterDTOparaCategoria(categoriaDTO);
        categoria.setId(id);

        categoria = this.service.atualizar(categoria);
        return ResponseEntity.noContent().build();

    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.remover(id);

        return ResponseEntity.noContent().build();
    }

}
