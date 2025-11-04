package com.wintech.portal.repository;

import com.wintech.portal.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository <Professor, Long> {
}
