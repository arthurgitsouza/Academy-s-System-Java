package com.wintech.portal.repository;

import com.wintech.portal.domain.Simulado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimuladoRepository extends JpaRepository<Simulado, Long> {

    @Query("SELECT s FROM Simulado s WHERE s.turma.id_turma = :idTurma")
    List<Simulado> findByTurmaId(@Param("idTurma") Long idTurma);
}