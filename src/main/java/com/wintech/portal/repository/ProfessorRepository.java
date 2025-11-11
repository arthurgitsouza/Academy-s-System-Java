package com.wintech.portal.repository;

import com.wintech.portal.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository <Professor, Long> {
    // Buscar professores por disciplina
    List<Professor> findByDisciplinaId(Long disciplinaId);

    // Buscar professor por nome
    List<Professor> findByNomeContainingIgnoreCase(String nome);

    // Buscar professor pelo e-mail (login/autenticação)
    Optional<Professor> findByEmail(String email);
}
