package com.wintech.portal.controller;

import com.wintech.portal.domain.Usuario;
import com.wintech.portal.dto.LoginRequestDTO;
import com.wintech.portal.dto.LoginResponseDTO;
import com.wintech.portal.security.JwtUtil; // <--- Mudou de JwtService para JwtUtil
import com.wintech.portal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil; // <--- Injetando o seu novo Util

    @Autowired
    public AuthController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // 1. Autentica
            Usuario usuarioAutenticado = usuarioService.fazerLogin(
                    loginRequest.getEmail(),
                    loginRequest.getSenha()
            );

            // 2. Gera o Token (AGORA USANDO O SEU NOVO MÉTODO)
            // Passamos o email e o perfil separadamente, como seu JwtUtil pede
            String token = jwtUtil.gerarToken(
                    usuarioAutenticado.getEmail(),
                    usuarioAutenticado.getPerfil()
            );

            // 3. Monta a resposta
            LoginResponseDTO response = new LoginResponseDTO(
                    token,
                    usuarioAutenticado.getId_usuario(),
                    usuarioAutenticado.getNome(),
                    usuarioAutenticado.getEmail(),
                    usuarioAutenticado.getPerfil()
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: " + e.getMessage());
        }
    }
}