package com.wintech.portal.controller;

import com.wintech.portal.domain.Usuario;
import com.wintech.portal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map; // Usaremos um Map para simplificar o recebimento do login

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // O "cérebro" que este controller vai usar
    private final UsuarioService usuarioService;

    // Injeção do service pelo construtor (o Spring faz isso automaticamente)
    @Autowired
    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /**
     * Endpoint de Login.
     * @PostMapping: Responde a requisições do tipo POST (envio de dados).
     * "/login": É o caminho final do endpoint. (URL completa: /api/auth/login)
     */
    @PostMapping("/login")
    public ResponseEntity<?> fazerLogin(@RequestBody Map<String, String> loginRequest) {
        // @RequestBody: Pega o JSON enviado pelo front-end e transforma em um Map.
        // Esperamos um JSON como: { "email": "...", "senha": "..." }

        try {
            String email = loginRequest.get("email");
            String senha = loginRequest.get("senha");

            // 1. Chama o Service para executar a lógica de negócio (que a outra equipe fará)
            Usuario usuarioAutenticado = usuarioService.fazerLogin(email, senha);

            // 2. Se der certo, retorna 200 OK com os dados do usuário.
            return ResponseEntity.ok(usuarioAutenticado);

        } catch (Exception e) {
            // 3. Se o Service lançar um erro (ex: senha errada), retorna 401 Unauthorized.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Erro: Email ou senha inválidos.");
        }
    }
}