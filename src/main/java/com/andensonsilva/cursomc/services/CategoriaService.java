package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.repositories.CategoriaRepository;
import com.andensonsilva.cursomc.services.exceptions.DataIntegrityException;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repository;

    public Categoria buscar(Integer id) {

        Optional<Categoria> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Categoria não encontrada. Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public Categoria inserir(Categoria c) {
        c.setId(null);
        return this.repository.save(c);
    }

    public Categoria atualizar(Categoria c) {
        this.buscar(c.getId());
        return this.repository.save(c);
    }

    public void remover(Integer id) {
        buscar(id);
            try{

                this.repository.deleteById(id);
            } catch (DataIntegrityViolationException e) {
                throw new DataIntegrityException("Não é possível excluir uma categoria que possue produtos");
            }
    }

    public List<Categoria> listar() {
        return  this.repository.findAll();
    }

    public Page<Categoria> encontrarPagina(Integer page, Integer size, String orderBy, String order) {

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(order), orderBy);
        return repository.findAll(pageRequest);
    }

}
