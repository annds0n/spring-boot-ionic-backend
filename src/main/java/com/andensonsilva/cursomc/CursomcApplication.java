package com.andensonsilva.cursomc;

import com.andensonsilva.cursomc.domain.Categoria;
import com.andensonsilva.cursomc.domain.Produto;
import com.andensonsilva.cursomc.repositories.CategoriaRepository;
import com.andensonsilva.cursomc.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository repo;

    @Autowired
    private ProdutoRepository prodRepo;

    public static void main(String[] args) {
        SpringApplication.run(CursomcApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");

        Produto prod1 = new Produto(null, "computador", 1239.00);
        Produto prod2 = new Produto(null, "Tv", 800.00);
        Produto prod3 = new Produto(null, "Geladeira", 3002.00);

        cat1.getProdutos().addAll(Arrays.asList(prod1, prod2, prod3));
        cat2.getProdutos().addAll(Arrays.asList(prod3));

        prod1.getCategorias().addAll(Arrays.asList(cat1, cat2));
        prod1.getCategorias().addAll(Arrays.asList(cat2, cat1));

        prod3.getCategorias().addAll(Arrays.asList(cat1));

        this.repo.saveAll(Arrays.asList(cat1, cat2));
        this.prodRepo.saveAll(Arrays.asList(prod1, prod2, prod3));

    }
}
