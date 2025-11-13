package com.wintech.portal.repository;

import com.wintech.portal.domain.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DisciplinaRepository extends JpaRepository <Disciplina,Long> {

}
