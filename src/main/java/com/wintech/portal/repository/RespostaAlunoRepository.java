package com.wintech.portal.repository;

import com.wintech.portal.domain.RespostaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaAlunoRepository extends JpaRepository <RespostaAluno, Long>{
    // Buscar respostas de um aluno em um simulado específico
    List<RespostaAluno> findByAlunoIdAndSimuladoId(Long alunoId, Long simuladoId);

    // Buscar todas as respostas de um mesmo aluno
    List<RespostaAluno> findByAlunoId(Long alunoId);

    // Buscar respostas para uma questão específica
    List<RespostaAluno> findByQuestaoId(Long questaoId);
}
