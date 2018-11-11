package com.andensonsilva.cursomc.services;

import com.andensonsilva.cursomc.security.Usuario;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioService {

    public static Usuario authenticated() {

        try {

            return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
