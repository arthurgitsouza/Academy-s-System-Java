package com.wintech.portal.repository;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {

    // Buscar histórico de comportamento do aluno ordenado por bimestre descendente
    List<Comportamento> findByAlunoOrderByAnoLetivoDescBimestreDesc(Aluno aluno);

    // Buscar comportamento de um aluno em um bimestre específico
    Optional<Comportamento> findByAlunoAndAnoLetivoAndBimestre(Aluno aluno, Integer anoLetivo, Integer bimestre);

    // Buscar todos os comportamentos de um aluno em um ano letivo
    List<Comportamento> findByAlunoAndAnoLetivo(Aluno aluno, Integer anoLetivo);
}