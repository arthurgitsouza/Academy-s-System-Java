package com.wintech.portal.repository;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Turma;
import com.wintech.portal.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>, JpaSpecificationExecutor<Aluno> {
    // Buscar alunos por turma
    List<Aluno> findByTurma(Turma turma);

    // Buscar aluno pelo usuário (NOVO)
    Optional<Aluno> findByUsuario(Usuario usuario);
}