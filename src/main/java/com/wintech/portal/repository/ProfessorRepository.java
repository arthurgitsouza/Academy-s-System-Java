package com.wintech.portal.repository;

import com.wintech.portal.domain.Professor;
import com.wintech.portal.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByUsuario(Usuario usuario);
}