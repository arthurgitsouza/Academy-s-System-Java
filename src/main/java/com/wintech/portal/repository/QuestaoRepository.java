package com.wintech.portal.repository;

import com.wintech.portal.domain.Questao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuestaoRepository extends JpaRepository <Questao, Long> {
    // Buscar questões por parte do enunciado (útil pra banco de questões)
    List<Questao> findByEnunciadoContainingIgnoreCase(String texto);

    // Buscar questões vinculadas a um simulado
    List<Questao> findBySimuladosId(Long simuladoId);

    // Buscar questões por disciplina
    List<Questao> findByDisciplinaId(Long disciplinaId);
}
