package com.wintech.portal.repository;

import com.wintech.portal.domain.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TurmaRepository extends JpaRepository <Turma,Long> {
    // Buscar turmas por ano letivo
    List<Turma> findByAnoLetivo(Integer anoLetivo);

    // Buscar turmas por série (ex: 1º, 2º, 3º ano)
    List<Turma> findBySerie(String serie);

    // Buscar turmas por professor (para relatórios)
    List<Turma> findByProfessorId(Long professorId);
}
