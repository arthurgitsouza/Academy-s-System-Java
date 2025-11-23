package com.wintech.portal.service;

import com.wintech.portal.domain.Aluno;
import com.wintech.portal.dto.*;
import com.wintech.portal.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

// ========================================
// SERVICE: Dashboard Professor
// ========================================
@Service
public class DashboardProfessorService {

    private final AlunoRepository alunoRepository;

    @Autowired
    public DashboardProfessorService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    /**
     * Listar alunos com filtros e paginação
     */
    public Page<AlunoCardDTO> listarAlunosComFiltro(FiltroAlunosDTO filtro, Pageable pageable) {

        // Criar Specification dinâmica para filtros
        Specification<Aluno> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por turma
            if (filtro.getIdTurma() != null) {
                predicates.add(cb.equal(root.get("turma").get("id_turma"), filtro.getIdTurma()));
            }

            // Filtro por busca no nome
            if (filtro.getBusca() != null && !filtro.getBusca().isEmpty()) {
                predicates.add(cb.like(
                        cb.lower(root.get("usuario").get("nome")),
                        "%" + filtro.getBusca().toLowerCase() + "%"
                ));
            }

            // Filtro por status de comportamento (calculado depois)
            // TODO: implementar se necessário como query nativa

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        // Buscar com paginação
        Page<Aluno> alunosPage = alunoRepository.findAll(spec, pageable);

        // Converter para DTO
        return alunosPage.map(AlunoCardDTO::new);
    }

    /**
     * Buscar turmas do professor
     */
    public List<TurmaSimplificadaDTO> buscarTurmasDoProfessor(Long idProfessor) {
        // TODO: Implementar consulta real
        List<TurmaSimplificadaDTO> turmas = new ArrayList<>();
        turmas.add(new TurmaSimplificadaDTO(1L, "9 ano A"));
        turmas.add(new TurmaSimplificadaDTO(2L, "9 ano B"));
        return turmas;
    }
}