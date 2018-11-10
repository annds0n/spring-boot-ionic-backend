package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.domain.Cliente;
import com.andensonsilva.cursomc.repositories.ClienteRepository;
import com.andensonsilva.cursomc.security.Usuario;
import com.andensonsilva.cursomc.services.validations.ClienteUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente == null) {
         throw new UsernameNotFoundException(email);
        }

        return new Usuario(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
    }
}
