package com.wintech.portal.repository;

import com.wintech.portal.domain.Simulado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SimuladoRepository extends JpaRepository <Simulado, Long> {
    // Buscar simulados por título/descrição parcial
    List<Simulado> findByTituloContainingIgnoreCase(String titulo);

    // Buscar simulados que contenham uma questão específica
    List<Simulado> findByQuestoesId(Long questaoId);

    // Buscar simulados associados a uma turma
    List<Simulado> findByTurmaId(Long turmaId);
}
