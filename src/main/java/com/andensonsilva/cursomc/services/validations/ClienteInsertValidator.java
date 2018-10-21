package com.andensonsilva.cursomc.services.validations;

import com.andensonsilva.cursomc.domain.enums.TipoCliente;
import com.andensonsilva.cursomc.dto.ClienteNewDTO;
import com.andensonsilva.cursomc.resources.exceptions.FieldMessage;
import com.andensonsilva.cursomc.services.validations.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

    @Override
    public boolean isValid(ClienteNewDTO clienteNewDTO, ConstraintValidatorContext constraintValidatorContext) {

        List<FieldMessage> list = new ArrayList<>();
        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAFISICA.getCodigo()) && !BR.isValidCPF(clienteNewDTO.getCpfOuCnpj())) {
            System.out.println(BR.isValidCPF(clienteNewDTO.getCpfOuCnpj()));
            list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
        }

        if(clienteNewDTO.getTipoCliente().equals(TipoCliente.PESSOAJURIDICA.getCodigo()) && !BR.isValidCNPJ(clienteNewDTO.getCpfOuCnpj())) {
            list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
        }

            for (FieldMessage f : list) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(f.getNessage()).addPropertyNode(f.getFieldName()).addConstraintViolation();
            }

        return list.isEmpty();
    }

    @Override
    public void initialize(ClienteInsert constraintAnnotation) {

    }
}
