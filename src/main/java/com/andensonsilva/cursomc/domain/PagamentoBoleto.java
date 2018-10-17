package com.andensonsilva.cursomc.domain;

import com.andensonsilva.cursomc.domain.enums.EstadoPagamento;

import java.util.Date;

public class PagamentoBoleto extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Date dataVencimento;
    private Date dataPagamento;

    public PagamentoBoleto(Integer id, EstadoPagamento estadoPapamento, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estadoPapamento, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }
}
