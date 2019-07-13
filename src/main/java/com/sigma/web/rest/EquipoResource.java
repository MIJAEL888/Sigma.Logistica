package com.sigma.web.rest;

import com.sigma.domain.Equipo;
import com.sigma.repository.EquipoRepository;
import com.sigma.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sigma.domain.Equipo}.
 */
@RestController
@RequestMapping("/api")
public class EquipoResource {

    private final Logger log = LoggerFactory.getLogger(EquipoResource.class);

    private static final String ENTITY_NAME = "logisticaEquipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipoRepository equipoRepository;

    public EquipoResource(EquipoRepository equipoRepository) {
        this.equipoRepository = equipoRepository;
    }

    /**
     * {@code POST  /equipos} : Create a new equipo.
     *
     * @param equipo the equipo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipo, or with status {@code 400 (Bad Request)} if the equipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipos")
    public ResponseEntity<Equipo> createEquipo(@Valid @RequestBody Equipo equipo) throws URISyntaxException {
        log.debug("REST request to save Equipo : {}", equipo);
        if (equipo.getId() != null) {
            throw new BadRequestAlertException("A new equipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipo result = equipoRepository.save(equipo);
        return ResponseEntity.created(new URI("/api/equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipos} : Updates an existing equipo.
     *
     * @param equipo the equipo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipo,
     * or with status {@code 400 (Bad Request)} if the equipo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipos")
    public ResponseEntity<Equipo> updateEquipo(@Valid @RequestBody Equipo equipo) throws URISyntaxException {
        log.debug("REST request to update Equipo : {}", equipo);
        if (equipo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Equipo result = equipoRepository.save(equipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, equipo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipos} : get all the equipos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipos in body.
     */
    @GetMapping("/equipos")
    public ResponseEntity<List<Equipo>> getAllEquipos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Equipos");
        Page<Equipo> page = equipoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /equipos/:id} : get the "id" equipo.
     *
     * @param id the id of the equipo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipos/{id}")
    public ResponseEntity<Equipo> getEquipo(@PathVariable Long id) {
        log.debug("REST request to get Equipo : {}", id);
        Optional<Equipo> equipo = equipoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipo);
    }

    /**
     * {@code DELETE  /equipos/:id} : delete the "id" equipo.
     *
     * @param id the id of the equipo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipos/{id}")
    public ResponseEntity<Void> deleteEquipo(@PathVariable Long id) {
        log.debug("REST request to delete Equipo : {}", id);
        equipoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
