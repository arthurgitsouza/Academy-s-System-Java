package com.wintech.portal.service;

import com.wintech.portal.domain.Usuario;
import com.wintech.portal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Realiza o login do usuário.
     * Valida o email e a senha.
     *
     * @param email Email do usuário
     * @param senha Senha em texto plano (não criptografada)
     * @return Usuario autenticado
     * @throws RuntimeException se o email não existir ou a senha estiver incorreta
     */
    public Usuario fazerLogin(String email, String senha) {
        // 1. Buscar o usuário pelo email
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o email: " + email));

        // 2. Verificar se a senha fornecida bate com o hash armazenado
        if (!passwordEncoder.matches(senha, usuario.getSenhaHash())) {
            throw new RuntimeException("Senha incorreta");
        }

        // 3. Verificar se o usuário está ativo
        if (usuario.getAtivo() == null || !usuario.getAtivo()) {
            throw new RuntimeException("Usuário desativado");
        }

        // 4. Se passou todas as validações, retorna o usuário
        return usuario;
    }
}