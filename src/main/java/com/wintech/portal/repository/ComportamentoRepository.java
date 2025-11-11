package com.wintech.portal.repository;

import com.wintech.portal.domain.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComportamentoRepository extends JpaRepository<ComportamentoRepository, Long> {
    // Buscar registros de comportamento por aluno
    List<Comportamento> findByAlunoId(Long alunoId);

    // Buscar registros de comportamento por disciplina
    List<Comportamento> findByDisciplinaId(Long disciplinaId);
}
