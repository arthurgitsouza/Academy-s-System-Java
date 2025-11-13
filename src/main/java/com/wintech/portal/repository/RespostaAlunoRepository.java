package com.wintech.portal.repository;

import com.wintech.portal.domain.RespostaAluno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RespostaAlunoRepository extends JpaRepository <RespostaAluno, Long>{

}
