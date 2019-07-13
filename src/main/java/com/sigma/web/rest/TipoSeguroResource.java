package com.sigma.web.rest;

import com.sigma.domain.TipoSeguro;
import com.sigma.repository.TipoSeguroRepository;
import com.sigma.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.sigma.domain.TipoSeguro}.
 */
@RestController
@RequestMapping("/api")
public class TipoSeguroResource {

    private final Logger log = LoggerFactory.getLogger(TipoSeguroResource.class);

    private static final String ENTITY_NAME = "logisticaTipoSeguro";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoSeguroRepository tipoSeguroRepository;

    public TipoSeguroResource(TipoSeguroRepository tipoSeguroRepository) {
        this.tipoSeguroRepository = tipoSeguroRepository;
    }

    /**
     * {@code POST  /tipo-seguros} : Create a new tipoSeguro.
     *
     * @param tipoSeguro the tipoSeguro to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoSeguro, or with status {@code 400 (Bad Request)} if the tipoSeguro has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-seguros")
    public ResponseEntity<TipoSeguro> createTipoSeguro(@Valid @RequestBody TipoSeguro tipoSeguro) throws URISyntaxException {
        log.debug("REST request to save TipoSeguro : {}", tipoSeguro);
        if (tipoSeguro.getId() != null) {
            throw new BadRequestAlertException("A new tipoSeguro cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoSeguro result = tipoSeguroRepository.save(tipoSeguro);
        return ResponseEntity.created(new URI("/api/tipo-seguros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-seguros} : Updates an existing tipoSeguro.
     *
     * @param tipoSeguro the tipoSeguro to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoSeguro,
     * or with status {@code 400 (Bad Request)} if the tipoSeguro is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoSeguro couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-seguros")
    public ResponseEntity<TipoSeguro> updateTipoSeguro(@Valid @RequestBody TipoSeguro tipoSeguro) throws URISyntaxException {
        log.debug("REST request to update TipoSeguro : {}", tipoSeguro);
        if (tipoSeguro.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoSeguro result = tipoSeguroRepository.save(tipoSeguro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, tipoSeguro.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-seguros} : get all the tipoSeguros.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoSeguros in body.
     */
    @GetMapping("/tipo-seguros")
    public List<TipoSeguro> getAllTipoSeguros() {
        log.debug("REST request to get all TipoSeguros");
        return tipoSeguroRepository.findAll();
    }

    /**
     * {@code GET  /tipo-seguros/:id} : get the "id" tipoSeguro.
     *
     * @param id the id of the tipoSeguro to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoSeguro, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-seguros/{id}")
    public ResponseEntity<TipoSeguro> getTipoSeguro(@PathVariable Long id) {
        log.debug("REST request to get TipoSeguro : {}", id);
        Optional<TipoSeguro> tipoSeguro = tipoSeguroRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoSeguro);
    }

    /**
     * {@code DELETE  /tipo-seguros/:id} : delete the "id" tipoSeguro.
     *
     * @param id the id of the tipoSeguro to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-seguros/{id}")
    public ResponseEntity<Void> deleteTipoSeguro(@PathVariable Long id) {
        log.debug("REST request to delete TipoSeguro : {}", id);
        tipoSeguroRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
