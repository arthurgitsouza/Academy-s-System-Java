package com.wintech.portal.repository;

import com.wintech.portal.domain.Simulado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SimuladoRepository extends JpaRepository <Simulado, Long> {

}
