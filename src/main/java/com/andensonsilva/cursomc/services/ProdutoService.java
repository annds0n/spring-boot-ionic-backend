package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.domain.Produto;
import com.andensonsilva.cursomc.repositories.CategoriaRepository;
import com.andensonsilva.cursomc.repositories.ProdutoRepository;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    ProdutoRepository repository;

    @Autowired
    CategoriaRepository categoriaRepository;

    public Produto buscar(Integer id) {

        Optional<Produto> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Produto não encontrado. Id: " + id + ", Tipo: " + Produto.class.getName()));
    }

    public Page<Produto> procurar(String nome, List<Integer> ids, Integer page, Integer size, String orderBy, String order) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(order), orderBy);

        List<Categoria> categorias = this.categoriaRepository.findAllById(ids);

        return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
    }

}
