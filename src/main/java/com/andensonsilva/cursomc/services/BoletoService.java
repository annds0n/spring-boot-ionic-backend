package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.PagamentoBoleto;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class BoletoService {

    public void preencherPagamentoBoleto(PagamentoBoleto pagamentoBoleto, Date instante) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(instante);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        pagamentoBoleto.setDataVencimento(calendar.getTime());
    }
}
