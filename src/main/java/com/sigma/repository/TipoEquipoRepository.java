package com.sigma.repository;

import com.sigma.domain.TipoEquipo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoEquipo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoEquipoRepository extends JpaRepository<TipoEquipo, Long> {

}
