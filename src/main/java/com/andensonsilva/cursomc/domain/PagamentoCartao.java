package com.andensonsilva.cursomc.domain;

import com.andensonsilva.cursomc.domain.enums.EstadoPagamento;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Entity;

@Entity
@JsonTypeName("pagamentoCartao")
public class PagamentoCartao extends Pagamento{

    private static final long serialVersionUID = 1L;

    private Integer numeroParcelas;

    public PagamentoCartao() { }

    public PagamentoCartao(Integer id, EstadoPagamento estadoPapamento, Pedido pedido, Integer numroParcelas) {
        super(id, estadoPapamento, pedido);
        this.numeroParcelas = numroParcelas;
    }

    public Integer getNumeroParcelas() {
        return numeroParcelas;
    }

    public void setNumeroParcelas(Integer numeroParcelas) {
        this.numeroParcelas = numeroParcelas;
    }
}
