package com.sigma.web.rest;

import com.sigma.domain.TipoEquipo;
import com.sigma.repository.TipoEquipoRepository;
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
 * REST controller for managing {@link com.sigma.domain.TipoEquipo}.
 */
@RestController
@RequestMapping("/api")
public class TipoEquipoResource {

    private final Logger log = LoggerFactory.getLogger(TipoEquipoResource.class);

    private static final String ENTITY_NAME = "logisticaTipoEquipo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoEquipoRepository tipoEquipoRepository;

    public TipoEquipoResource(TipoEquipoRepository tipoEquipoRepository) {
        this.tipoEquipoRepository = tipoEquipoRepository;
    }

    /**
     * {@code POST  /tipo-equipos} : Create a new tipoEquipo.
     *
     * @param tipoEquipo the tipoEquipo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoEquipo, or with status {@code 400 (Bad Request)} if the tipoEquipo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-equipos")
    public ResponseEntity<TipoEquipo> createTipoEquipo(@Valid @RequestBody TipoEquipo tipoEquipo) throws URISyntaxException {
        log.debug("REST request to save TipoEquipo : {}", tipoEquipo);
        if (tipoEquipo.getId() != null) {
            throw new BadRequestAlertException("A new tipoEquipo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoEquipo result = tipoEquipoRepository.save(tipoEquipo);
        return ResponseEntity.created(new URI("/api/tipo-equipos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-equipos} : Updates an existing tipoEquipo.
     *
     * @param tipoEquipo the tipoEquipo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoEquipo,
     * or with status {@code 400 (Bad Request)} if the tipoEquipo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoEquipo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-equipos")
    public ResponseEntity<TipoEquipo> updateTipoEquipo(@Valid @RequestBody TipoEquipo tipoEquipo) throws URISyntaxException {
        log.debug("REST request to update TipoEquipo : {}", tipoEquipo);
        if (tipoEquipo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoEquipo result = tipoEquipoRepository.save(tipoEquipo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoEquipo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-equipos} : get all the tipoEquipos.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoEquipos in body.
     */
    @GetMapping("/tipo-equipos")
    public ResponseEntity<List<TipoEquipo>> getAllTipoEquipos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of TipoEquipos");
        Page<TipoEquipo> page = tipoEquipoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tipo-equipos/:id} : get the "id" tipoEquipo.
     *
     * @param id the id of the tipoEquipo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoEquipo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-equipos/{id}")
    public ResponseEntity<TipoEquipo> getTipoEquipo(@PathVariable Long id) {
        log.debug("REST request to get TipoEquipo : {}", id);
        Optional<TipoEquipo> tipoEquipo = tipoEquipoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoEquipo);
    }

    /**
     * {@code DELETE  /tipo-equipos/:id} : delete the "id" tipoEquipo.
     *
     * @param id the id of the tipoEquipo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-equipos/{id}")
    public ResponseEntity<Void> deleteTipoEquipo(@PathVariable Long id) {
        log.debug("REST request to delete TipoEquipo : {}", id);
        tipoEquipoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
