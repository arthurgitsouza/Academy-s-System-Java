package com.wintech.portal.repository;

import com.wintech.portal.domain.Comportamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ComportamentoRepository extends JpaRepository<Comportamento, Long> {

}
