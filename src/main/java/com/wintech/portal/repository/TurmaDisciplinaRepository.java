package com.wintech.portal.repository;

import com.wintech.portal.domain.TurmaDisciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TurmaDisciplinaRepository extends JpaRepository <TurmaDisciplina,Long> {

}
