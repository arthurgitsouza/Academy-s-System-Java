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
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * IMPORTANTE: PasswordEncoder como primeiro Bean para evitar dependências circulares
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // ✅ Rotas públicas (sem autenticação)
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        // ✅ Rotas de Perfil (requer autenticação)
                        .requestMatchers("/api/meu-perfil/**").authenticated()
                        .requestMatchers("/api/perfil/**").authenticated()
                        .requestMatchers("/api/alunos/*/perfil").authenticated()
                        .requestMatchers("/api/professores/*/perfil").authenticated()
                        .requestMatchers("/api/admin/dashboard/alunos/*").authenticated()
                        .requestMatchers("/api/admin/dashboard/professores/*").authenticated()

                        // ✅ Rotas de Admin (requer autenticação)
                        .requestMatchers("/api/admin/**").authenticated()

                        // ✅ Rotas de Professor (requer autenticação)
                        .requestMatchers("/api/professor/**").authenticated()
                        .requestMatchers("/api/professores/**").authenticated()

                        // ✅ Rotas de Aluno (requer autenticação)
                        .requestMatchers("/api/aluno/**").authenticated()
                        .requestMatchers("/alunos/**").authenticated()

                        // ✅ Rotas de Simulados (requer autenticação)
                        .requestMatchers("/api/simulados/**").authenticated()

                        // ✅ Rotas de Upload (requer autenticação)
                        .requestMatchers("/api/upload/**").authenticated()

                        // ✅ Todas as outras requisições exigem autenticação
                        .anyRequest().authenticated()
                )
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // ✅ Origens permitidas (front-end)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",      // React padrão
                "http://localhost:5173",      // Vite
                "http://localhost:5174",      // Vite alternativo
                "https://daniela-chasmogamous-gabrielle.ngrok-free.dev" // Produção/Teste
        ));

        // ✅ Métodos HTTP permitidos
        configuration.setAllowedMethods(Arrays.asList(
                "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));

        // ✅ Headers permitidos
        configuration.setAllowedHeaders(Arrays.asList("*"));

        // ✅ Permite enviar credenciais (cookies, authorization headers)
        configuration.setAllowCredentials(true);

        // ✅ Headers expostos na resposta
        configuration.setExposedHeaders(Arrays.asList("Authorization"));

        // ✅ Tempo de cache da configuração CORS (1 hora)
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}