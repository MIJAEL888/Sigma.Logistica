package com.sigma.repository;

import com.sigma.domain.TipoSeguro;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoSeguro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoSeguroRepository extends JpaRepository<TipoSeguro, Long> {

}
