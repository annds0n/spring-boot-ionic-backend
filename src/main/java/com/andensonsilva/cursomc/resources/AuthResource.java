package com.andensonsilva.cursomc.resources;

import com.andensonsilva.cursomc.security.JWTUtil;
import com.andensonsilva.cursomc.security.Usuario;
import com.andensonsilva.cursomc.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class AuthResource {

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/refresh-token")
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        Usuario usuario = UsuarioService.authenticated();
        String token = jwtUtil.generateToken(usuario.getUsername());
        response.addHeader("Authorization", "Bearer "+token);
        return ResponseEntity.noContent().build();
    }
}
