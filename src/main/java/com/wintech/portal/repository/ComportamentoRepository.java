package com.wintech.portal.repository;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.domain.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {

    // ✅ CORRETO: Buscar histórico ordenado pela DATA (mais recente primeiro)
    // Substitui a busca antiga por Ano/Bimestre que causava erro
    List<Comportamento> findByAlunoOrderByDataRegistroDesc(Aluno aluno);

}