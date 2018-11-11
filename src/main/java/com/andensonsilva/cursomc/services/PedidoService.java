package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.*;
import com.andensonsilva.cursomc.domain.enums.EstadoPagamento;
import com.andensonsilva.cursomc.repositories.ItemPedidoRepository;
import com.andensonsilva.cursomc.repositories.PagamentoRepository;
import com.andensonsilva.cursomc.repositories.PedidoRepository;
import com.andensonsilva.cursomc.security.Usuario;
import com.andensonsilva.cursomc.services.exceptions.AuthorizationException;
import com.andensonsilva.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    // FIXME: Mudar para o service
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public PedidoService() {
    }

    public Pedido buscar(Integer id) {

        Optional<Pedido> cat = this.repository.findById(id);

        return cat.orElseThrow(() -> new ObjectNotFoundException(
                "Pedido não encontrado. Id: " + id + ", Tipo: " + Pedido.class.getName()));
    }

    @Transactional
    public Pedido inserir(Pedido pedido) {
        pedido.setId(null);
        pedido.setInstante(new Date());
        pedido.getPagamento().setEstadoPapamento(EstadoPagamento.PENDENTE);
        pedido.getPagamento().setPedido(pedido);
        if (pedido.getPagamento() instanceof PagamentoBoleto) {
            PagamentoBoleto pagamentoBoleto = (PagamentoBoleto) pedido.getPagamento();
            boletoService.preencherPagamentoBoleto(pagamentoBoleto, pedido.getInstante());
        }
        pedido = this.repository.save(pedido);
        pagamentoRepository.save(pedido.getPagamento());
        for (ItemPedido itemPedido : pedido.getItens()) {
            itemPedido.setDesconto(0.0);
            itemPedido.setPreco(produtoService.buscar(itemPedido.getProduto().getId()).getPreco());
            itemPedido.setPedido(pedido);
        }

        itemPedidoRepository.saveAll(pedido.getItens());

        return pedido;
    }

    public Page<Pedido> buscarPagina( Integer page, Integer size, String orderBy, String order) {

        Usuario usuario = UsuarioService.authenticated();

        if(usuario == null) {
            throw new AuthorizationException("Acesso negado");
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.valueOf(order), orderBy);

        Cliente cliente = clienteService.buscar(usuario.getId());


        return repository.findByCliente(cliente, pageRequest);
    }

}
