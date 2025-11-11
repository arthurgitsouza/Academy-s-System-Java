package com.wintech.portal.repository;

import com.wintech.portal.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository <DisciplinaRepository,Long> {
    // Buscar disciplinas de uma turma
    List<Disciplina> findByTurmasId(Long turmaId);

    // Buscar disciplinas por professor
    List<Disciplina> findByProfessorId(Long professorId);
}
