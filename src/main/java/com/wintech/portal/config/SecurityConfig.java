package com.wintech.portal.config;

import com.wintech.portal.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // Permite usar @PreAuthorize nos controllers
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Configuração principal de segurança da aplicação.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (não precisamos para APIs REST stateless)
                .csrf(csrf -> csrf.disable())

                // Configura CORS (permite requisições do front-end Angular)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                // Configura as regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso público ao endpoint de login
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()

                        // Permite acesso a recursos estáticos (se houver)
                        .requestMatchers("/api/public/**").permitAll()

                        // TODAS as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )

                // Configura sessão como STATELESS (não cria sessão no servidor)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Adiciona o filtro JWT ANTES do filtro padrão de autenticação
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configuração de CORS (Cross-Origin Resource Sharing).
     * Permite que o front-end (localhost:4200) faça requisições para o back-end.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições vindas do front-end React.js
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));

        // Permite todos os métodos HTTP (GET, POST, PUT, DELETE, etc.)
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        // Permite todos os headers
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // Permite enviar credenciais (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Bean do PasswordEncoder (para criptografar senhas).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}