package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.*;
import com.andensonsilva.cursomc.domain.enums.EstadoPagamento;
import com.andensonsilva.cursomc.domain.enums.Perfil;
import com.andensonsilva.cursomc.domain.enums.TipoCliente;
import com.andensonsilva.cursomc.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private CategoriaRepository categoriaRepository;

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

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public void instatiateDatabase() throws ParseException {
        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama mesa e banho");
        Categoria cat4 = new Categoria(null, "Perfumaria");
        Categoria cat5 = new Categoria(null, "Cosméticos");
        Categoria cat6 = new Categoria(null, "Eletrodomésticos");

        Produto prod1 = new Produto(null, "computador", 1239.00);
        Produto prod2 = new Produto(null, "Tv", 800.00);
        Produto prod3 = new Produto(null, "Panela", 302.20);
        Produto prod4 = new Produto(null, "Geladeira", 3002.00);
        Produto prod5 = new Produto(null, "Fogão", 908.00);
        Produto prod6 = new Produto(null, "Toalha", 60.00);
        Produto prod7 = new Produto(null, "Malbec Golden Shit", 300.00);
        Produto prod8 = new Produto(null, "Escrivania", 2420.00);
        Produto prod9 = new Produto(null, "Grampeador", 14.00);
        Produto prod10 = new Produto(null, "Mouse", 54.00);
        Produto prod11 = new Produto(null, "Batom", 30.00);

        cat1.getProdutos().addAll(Arrays.asList(prod1, prod10));
        cat2.getProdutos().addAll(Arrays.asList(prod8, prod9));
        cat3.getProdutos().addAll(Arrays.asList(prod3, prod6));
        cat4.getProdutos().addAll(Arrays.asList(prod7, prod11));
        cat5.getProdutos().addAll(Arrays.asList(prod11, prod7));
        cat6.getProdutos().addAll(Arrays.asList(prod4, prod5, prod2));

        prod1.getCategorias().addAll(Arrays.asList(cat1));
        prod2.getCategorias().addAll(Arrays.asList(cat6));
        prod3.getCategorias().addAll(Arrays.asList(cat3));
        prod4.getCategorias().addAll(Arrays.asList(cat6));
        prod5.getCategorias().addAll(Arrays.asList(cat6));
        prod6.getCategorias().addAll(Arrays.asList(cat3));
        prod7.getCategorias().addAll(Arrays.asList(cat4, cat5));
        prod8.getCategorias().addAll(Arrays.asList(cat2));
        prod9.getCategorias().addAll(Arrays.asList(cat2));
        prod10.getCategorias().addAll(Arrays.asList(cat1));
        prod11.getCategorias().addAll(Arrays.asList(cat4, cat5));

        this.categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6));
        this.prodRepo.saveAll(Arrays.asList(prod1, prod2, prod3, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9, prod10, prod11));

        Estado estado1 = new Estado(null, "Pernambuco");
        Estado estado2 = new Estado(null, "Ceará");

        Cidade cidade1 = new Cidade(null, "Recife", estado1);
        Cidade cidade2 = new Cidade(null, "Fortaleza", estado2);
        Cidade cidade3 = new Cidade(null, "Gravatá", estado1);

        estado1.getCidades().addAll(Arrays.asList(cidade1, cidade3));
        estado2.getCidades().addAll(Arrays.asList(cidade2));

        this.estadoRepository.saveAll(Arrays.asList(estado1, estado2));
        this.cidadeRepository.saveAll(Arrays.asList(cidade1, cidade2, cidade3));

        Cliente cliente1 = new Cliente(null, "Maria José", "marybb@gamil.com", "24513231123", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("Esta é uma senha"));
        cliente1.getTelefones().addAll(Arrays.asList("24354242", "34243243242"));
        cliente1.addPerfil(Perfil.CLIENTE);

        Cliente cliente2 = new Cliente(null, "Jainara Pereira", "jana.pere@gamil.com", "43196577058", TipoCliente.PESSOAFISICA, bCryptPasswordEncoder.encode("321"));
        cliente2.getTelefones().addAll(Arrays.asList("890830348", "78504545392"));
        cliente2.addPerfil(Perfil.ADMIN);

        Endereco endereco1 = new Endereco(null, "Rua Teste", "23", "apt", "Bairrinho", "45545-45", cliente1, cidade1);
        Endereco endereco2 = new Endereco(null, "Rua Teste 2", "24", "Mansão", "Olho roxo", "45545-78", cliente1, cidade2);
        Endereco endereco3 = new Endereco(null, "Rua Teste 3", "234", "Mansão 1", "Olho roxo 2", "45785-78", cliente2, cidade2);

        cliente1.getEnderecos().addAll(Arrays.asList(endereco1, endereco2));
        cliente2.getEnderecos().add(endereco3);

        this.clienteRepository.saveAll(Arrays.asList(cliente1, cliente2));
        this.enderecoRepository.saveAll(Arrays.asList(endereco1, endereco2, endereco3));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido pedido1 = new Pedido(null, simpleDateFormat.parse("30/09/2017 12:45"), cliente1, endereco1);
        Pedido pedido2 = new Pedido(null, simpleDateFormat.parse("30/09/2017 12:45"), cliente1, endereco2);


        Pagamento pagamento1 = new PagamentoCartao(null, EstadoPagamento.QUITADO, pedido1, 6);
        pedido1.setPagamento(pagamento1);

        Pagamento pagamento2 = new PagamentoBoleto(null, EstadoPagamento.PENDENTE, pedido2, simpleDateFormat.parse("20/10/2017 00:00"), null);
        pedido2.setPagamento(pagamento2);

        cliente1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));

        this.pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
        this.pagamentoRepository.saveAll(Arrays.asList(pagamento1, pagamento2));

        ItemPedido itemPedido1 = new ItemPedido(pedido1, prod1, 0.00, 1, 20000.00);
        ItemPedido itemPedido2 = new ItemPedido(pedido1, prod3, 0.00, 2, 80.00);
        ItemPedido itemPedido3 = new ItemPedido(pedido2, prod2, 100.00, 1, 800.00);

        pedido1.getItens().addAll(Arrays.asList(itemPedido1, itemPedido2));
        pedido2.getItens().addAll(Arrays.asList(itemPedido3));

        prod1.getItens().addAll(Arrays.asList(itemPedido1));
        prod2.getItens().addAll(Arrays.asList(itemPedido3));
        prod3.getItens().addAll(Arrays.asList(itemPedido2));

        this.itemPedidoRepository.saveAll(Arrays.asList(itemPedido1, itemPedido2, itemPedido3));
    }
}
