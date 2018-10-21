package com.andensonsilva.cursomc.services.validations;

import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.domain.enums.TipoCliente;
import com.andensonsilva.cursomc.dto.ClienteDTO;
import com.andensonsilva.cursomc.dto.ClienteNewDTO;
import com.andensonsilva.cursomc.repositories.ClienteRepository;
import com.andensonsilva.cursomc.resources.exceptions.FieldMessage;
import com.andensonsilva.cursomc.services.validations.utils.BR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    HttpServletRequest request;

    @Override
    public boolean isValid(ClienteDTO clienteDTO, ConstraintValidatorContext constraintValidatorContext) {

        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        List<FieldMessage> list = new ArrayList<>();

        Integer uriId = Integer.parseInt(map.get("id"));

        Cliente aux = this.clienteRepository.findByEmail(clienteDTO.getEmail());

        if(aux != null && !aux.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "E-mail já cadastrado"));
        }

            for (FieldMessage f : list) {
                constraintValidatorContext.disableDefaultConstraintViolation();
                constraintValidatorContext.buildConstraintViolationWithTemplate(f.getNessage()).addPropertyNode(f.getFieldName()).addConstraintViolation();
            }

        return list.isEmpty();
    }

    @Override
    public void initialize(ClienteUpdate constraintAnnotation) {

    }
}
