package com.sigma.repository;

import com.sigma.domain.Laboratorio;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Laboratorio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Long> {

}
