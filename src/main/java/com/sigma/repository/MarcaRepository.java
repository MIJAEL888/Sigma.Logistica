package com.sigma.repository;

import com.sigma.domain.Marca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Marca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

}
