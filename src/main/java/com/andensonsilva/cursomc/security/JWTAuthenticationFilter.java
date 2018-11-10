package com.andensonsilva.cursomc.security;

import com.andensonsilva.cursomc.dto.CredenciaisDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        try {
            // Pega o email e senha da requisição e converte para o tipo CredenciaisDTO
            CredenciaisDTO credenciaisDTO = new ObjectMapper()
                                                    .readValue(request.getInputStream(), CredenciaisDTO.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    credenciaisDTO.getEmail(), credenciaisDTO.getSenha(), new ArrayList<>()
            );

            Authentication authentication = authenticationManager.authenticate(token);
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String nomeUsuario = ((Usuario) authResult.getPrincipal()).getUsername(); // Email, nesse caso
        String token = jwtUtil.generateToken(nomeUsuario);
        response.addHeader("Authorization", "Bearer " + token);
    }

    /**
     * Trata o error na autenticação
     */
    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                           + "\"status\": 401, "
                           + "\"error\": \"Não autorizado\", "
                           + "\"message\": \"Email ou senha inválidos\", "
                           + "\"path\": \"/login\"}";
        }
    }
}
