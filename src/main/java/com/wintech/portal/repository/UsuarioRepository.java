package com.wintech.portal.repository;

import com.wintech.portal.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository <Usuario,Long> {
    // Buscar usuário pelo e-mail (login)
    Optional<Usuario> findByEmail(String email);

    // Verificar se e-mail já existe (para cadastro)
    boolean existsByEmail(String email);

    // Buscar usuários por tipo/perfil (professor, coordenador, aluno)
    List<Usuario> findByPerfil(String perfil);

    // Buscar usuários por nome
    List<Usuario> findByNomeContainingIgnoreCase(String nome);
}
