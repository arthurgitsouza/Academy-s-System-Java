package com.wintech.portal.repository;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    // ADICIONE ESTE MÉTODO
    List<Aluno> findByTurma(Turma turma);
}