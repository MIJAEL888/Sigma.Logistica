package com.sigma.web.rest;

import com.sigma.domain.Monitorista;
import com.sigma.repository.MonitoristaRepository;
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
 * REST controller for managing {@link com.sigma.domain.Monitorista}.
 */
@RestController
@RequestMapping("/api")
public class MonitoristaResource {

    private final Logger log = LoggerFactory.getLogger(MonitoristaResource.class);

    private static final String ENTITY_NAME = "logisticaMonitorista";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MonitoristaRepository monitoristaRepository;

    public MonitoristaResource(MonitoristaRepository monitoristaRepository) {
        this.monitoristaRepository = monitoristaRepository;
    }

    /**
     * {@code POST  /monitoristas} : Create a new monitorista.
     *
     * @param monitorista the monitorista to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new monitorista, or with status {@code 400 (Bad Request)} if the monitorista has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/monitoristas")
    public ResponseEntity<Monitorista> createMonitorista(@Valid @RequestBody Monitorista monitorista) throws URISyntaxException {
        log.debug("REST request to save Monitorista : {}", monitorista);
        if (monitorista.getId() != null) {
            throw new BadRequestAlertException("A new monitorista cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Monitorista result = monitoristaRepository.save(monitorista);
        return ResponseEntity.created(new URI("/api/monitoristas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /monitoristas} : Updates an existing monitorista.
     *
     * @param monitorista the monitorista to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated monitorista,
     * or with status {@code 400 (Bad Request)} if the monitorista is not valid,
     * or with status {@code 500 (Internal Server Error)} if the monitorista couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/monitoristas")
    public ResponseEntity<Monitorista> updateMonitorista(@Valid @RequestBody Monitorista monitorista) throws URISyntaxException {
        log.debug("REST request to update Monitorista : {}", monitorista);
        if (monitorista.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Monitorista result = monitoristaRepository.save(monitorista);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, monitorista.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /monitoristas} : get all the monitoristas.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of monitoristas in body.
     */
    @GetMapping("/monitoristas")
    public ResponseEntity<List<Monitorista>> getAllMonitoristas(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Monitoristas");
        Page<Monitorista> page;
        if (eagerload) {
            page = monitoristaRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = monitoristaRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /monitoristas/:id} : get the "id" monitorista.
     *
     * @param id the id of the monitorista to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the monitorista, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/monitoristas/{id}")
    public ResponseEntity<Monitorista> getMonitorista(@PathVariable Long id) {
        log.debug("REST request to get Monitorista : {}", id);
        Optional<Monitorista> monitorista = monitoristaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(monitorista);
    }

    /**
     * {@code DELETE  /monitoristas/:id} : delete the "id" monitorista.
     *
     * @param id the id of the monitorista to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/monitoristas/{id}")
    public ResponseEntity<Void> deleteMonitorista(@PathVariable Long id) {
        log.debug("REST request to delete Monitorista : {}", id);
        monitoristaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
