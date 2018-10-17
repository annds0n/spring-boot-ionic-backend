package com.andensonsilva.cursomc;

import com.andensonsilva.cursomc.domain.*;
import com.andensonsilva.cursomc.domain.enums.TipoCliente;
import com.andensonsilva.cursomc.repositories.*;
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

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

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

        Estado estado1 = new Estado(null, "Pernambuco");
        Estado estado2 = new Estado(null, "Ceará");

        Cidade cidade1 = new Cidade(null, "Recife", estado1);
        Cidade cidade2 = new Cidade(null, "Fortaleza", estado2);
        Cidade cidade3 = new Cidade(null, "Gravatá", estado1);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade3));
        estado2.getCidades().addAll(Arrays.asList(cidade2));

        this.estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        this.cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Maria José", "marybb@gamil.com", "24513231123", TipoCliente.PESSOAFISICA);

        cliente1.getTelefones().addAll(Arrays.asList("24354242", "34243243242"));


        Endereco endereco1 =  new Endereco(null, "Rua Teste", "23", "apt", "Bairrinho", "45545-45", cliente1, cidade1);
        Endereco endereco2 =  new Endereco(null, "Rua Teste 2", "24", "Mansão", "Olho roxo", "45545-78", cliente1, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));

        this.clienteRepository.save(cliente1);
        this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2));



    }
}
