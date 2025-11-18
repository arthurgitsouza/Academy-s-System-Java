package com.wintech.portal.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Esta chave será lida do application.properties
    @Value("${jwt.secret}")
    private String secret;

    // Tempo de expiração: 24 horas (em milissegundos)
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * Gera um token JWT para o usuário autenticado.
     * O token contém o email do usuário e o perfil (role).
     */
    public String gerarToken(String email, String perfil) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(email) // O "dono" do token (quem está autenticado)
                .claim("perfil", perfil) // Informação adicional (role)
                .issuedAt(new Date()) // Data de criação
                .expiration(new Date(System.currentTimeMillis() + expiration)) // Data de expiração
                .signWith(key) // Assina o token com a chave secreta
                .compact(); // Gera a string final do JWT
    }

    /**
     * Extrai o email (subject) de dentro do token JWT.
     */
    public String extrairEmail(String token) {
        return extrairClaims(token).getSubject();
    }

    /**
     * Extrai o perfil (role) de dentro do token JWT.
     */
    public String extrairPerfil(String token) {
        return extrairClaims(token).get("perfil", String.class);
    }

    /**
     * Valida se o token é válido (não expirou e a assinatura está correta).
     */
    public boolean validarToken(String token) {
        try {
            extrairClaims(token); // Se conseguir extrair, o token é válido
            return true;
        } catch (Exception e) {
            return false; // Token inválido ou expirado
        }
    }

    /**
     * Método auxiliar que extrai todas as informações (claims) do token.
     */
    private Claims extrairClaims(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

