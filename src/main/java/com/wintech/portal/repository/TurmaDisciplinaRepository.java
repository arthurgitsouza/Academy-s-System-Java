package com.wintech.portal.repository;

import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TurmaDisciplinaRepository extends JpaRepository<TurmaDisciplina, Long> {

    List<TurmaDisciplina> findByProfessor(Professor professor);
}