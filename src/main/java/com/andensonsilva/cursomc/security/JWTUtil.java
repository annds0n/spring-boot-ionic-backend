package com.andensonsilva.cursomc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String userName){
        return Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();

    }

    public boolean tokenValido(String token) {
        /**
         * Armazena as "reinvindicações"
         * Armazena informações como usuário e tempo de expiração do token
         */
        Claims claims = getClaimsToken(token);
        if(claims != null) {
            String userName = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now =  new Date(System.currentTimeMillis());
            if(userName != null && expirationDate != null && now.before(expirationDate)){
                return true;
            }
        }
        return false;
    }

    /**
     * Obtém as informações do token
     * @param token
     * @return
     */
    private Claims getClaimsToken(String token) {
        try {

            return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public String getUserName(String token) {
        Claims claims = getClaimsToken(token);
        if(claims != null) {
            return claims.getSubject();
        }

        return null;
    }
}
