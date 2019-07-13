package com.sigma.repository;

import com.sigma.domain.Monitorista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Monitorista entity.
 */
@Repository
public interface MonitoristaRepository extends JpaRepository<Monitorista, Long> {

    @Query(value = "select distinct monitorista from Monitorista monitorista left join fetch monitorista.tipoSeguros",
        countQuery = "select count(distinct monitorista) from Monitorista monitorista")
    Page<Monitorista> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct monitorista from Monitorista monitorista left join fetch monitorista.tipoSeguros")
    List<Monitorista> findAllWithEagerRelationships();

    @Query("select monitorista from Monitorista monitorista left join fetch monitorista.tipoSeguros where monitorista.id =:id")
    Optional<Monitorista> findOneWithEagerRelationships(@Param("id") Long id);

}
