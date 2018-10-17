package com.andensonsilva.cursomc.domain;

import com.andensonsilva.cursomc.domain.enums.EstadoPagamento;

public class PagamentoCartao extends Pagamento{

    private static final long serialVersionUID = 1L;

    private Integer numroParcelas;

    public PagamentoCartao() { }

    public PagamentoCartao(Integer id, EstadoPagamento estadoPapamento, Pedido pedido, Integer numroParcelas) {
        super(id, estadoPapamento, pedido);
        this.numroParcelas = numroParcelas;
    }

    public Integer getNumroParcelas() {
        return numroParcelas;
    }

    public void setNumroParcelas(Integer numroParcelas) {
        this.numroParcelas = numroParcelas;
    }
}
