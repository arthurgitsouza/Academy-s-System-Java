package com.wintech.portal.security;

import com.wintech.portal.repository.UsuarioRepository;
import com.wintech.portal.domain.Usuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Este método é executado ANTES de cada requisição chegar no Controller.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1. Pegar o header "Authorization" da requisição
        String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwt = null;

        // 2. Verificar se o header existe e começa com "Bearer "
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Remove "Bearer " e pega só o token

            try {
                // 3. Extrair o email (subject) de dentro do token
                email = jwtUtil.extrairEmail(jwt);
            } catch (Exception e) {
                // Token inválido ou expirado - não faz nada, deixa passar
                // O Spring Security vai bloquear depois se a rota for protegida
            }
        }

        // 4. Se conseguiu extrair o email E o usuário ainda não está autenticado
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // 5. Buscar o usuário no banco de dados
            Usuario usuario = usuarioRepository.findByEmail(email).orElse(null);

            // 6. Validar o token
            if (usuario != null && jwtUtil.validarToken(jwt)) {

                // 7. Extrair o perfil (role) do token
                String perfil = jwtUtil.extrairPerfil(jwt);

                // 8. Criar a "autoridade" (role) no formato que o Spring Security entende
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + perfil);

                // 9. Criar o objeto de autenticação
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                usuario, // Principal (o usuário autenticado)
                                null,    // Credentials (não precisamos da senha aqui)
                                Collections.singletonList(authority) // Authorities (as permissões)
                        );

                // 10. Adicionar detalhes da requisição (IP, Session ID, etc.)
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 11. AUTENTICAR o usuário no contexto do Spring Security
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // 12. Continuar a cadeia de filtros (deixar a requisição seguir)
        filterChain.doFilter(request, response);
    }
}