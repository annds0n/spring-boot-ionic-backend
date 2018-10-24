package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.domain.Produto;
import com.andensonsilva.cursomc.domain.Produto;
import com.andensonsilva.cursomc.dto.ProdutoDTO;
import com.andensonsilva.cursomc.resources.utils.URL;
import com.andensonsilva.cursomc.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;


    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Integer id) {

        Produto produto = this.service.buscar(id);

        return ResponseEntity.ok().body(produto);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<ProdutoDTO>> findPage(
            @RequestParam(defaultValue = "") String nome,
            @RequestParam(defaultValue = "") String categorias,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "4") Integer size,
            @RequestParam(defaultValue = "nome") String orderBy,
            @RequestParam(defaultValue = "ASC") String order
    ) {

        List<Integer> ids = URL.listIntegerDecoder(categorias);

        String nomeDecoded = URL.decodeParam(nome);

        Page<Produto> produtoPage = this.service.procurar(nomeDecoded, ids, page, size, orderBy, order);

        Page<ProdutoDTO> produtoDTOs = produtoPage.map(p -> new ProdutoDTO(p));

        return ResponseEntity.ok().body(produtoDTOs);
    }

}
