package com.wintech.portal.repository;

import com.wintech.portal.domain.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository <Aluno, Long>{
    // Busca por nome (RF001)
    List<Aluno> findByNomeContainingIgnoreCase(String nome);

    // Busca por turma (RF002)
    List<Aluno> findByTurmaId(Long turmaId);

    // Busca por faixa etária (caso precise filtrar por idade)
    List<Aluno> findByIdadeBetween(Integer idadeMin, Integer idadeMax);

    // Buscar aluno por comportamento (liga com histórico)
    List<Aluno> findByComportamentosId(Long comportamentoId);

}
