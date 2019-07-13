package com.sigma.web.rest;

import com.sigma.LogisticaApp;
import com.sigma.domain.TipoEquipo;
import com.sigma.repository.TipoEquipoRepository;
import com.sigma.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.sigma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TipoEquipoResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TipoEquipoResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoEquipoRepository tipoEquipoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restTipoEquipoMockMvc;

    private TipoEquipo tipoEquipo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoEquipoResource tipoEquipoResource = new TipoEquipoResource(tipoEquipoRepository);
        this.restTipoEquipoMockMvc = MockMvcBuilders.standaloneSetup(tipoEquipoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEquipo createEntity(EntityManager em) {
        TipoEquipo tipoEquipo = new TipoEquipo()
            .nombre(DEFAULT_NOMBRE)
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoEquipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoEquipo createUpdatedEntity(EntityManager em) {
        TipoEquipo tipoEquipo = new TipoEquipo()
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);
        return tipoEquipo;
    }

    @BeforeEach
    public void initTest() {
        tipoEquipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoEquipo() throws Exception {
        int databaseSizeBeforeCreate = tipoEquipoRepository.findAll().size();

        // Create the TipoEquipo
        restTipoEquipoMockMvc.perform(post("/api/tipo-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEquipo)))
            .andExpect(status().isCreated());

        // Validate the TipoEquipo in the database
        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoEquipo testTipoEquipo = tipoEquipoList.get(tipoEquipoList.size() - 1);
        assertThat(testTipoEquipo.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoEquipo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoEquipo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoEquipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoEquipoRepository.findAll().size();

        // Create the TipoEquipo with an existing ID
        tipoEquipo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoEquipoMockMvc.perform(post("/api/tipo-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEquipo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEquipo in the database
        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoEquipoRepository.findAll().size();
        // set the field null
        tipoEquipo.setNombre(null);

        // Create the TipoEquipo, which fails.

        restTipoEquipoMockMvc.perform(post("/api/tipo-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEquipo)))
            .andExpect(status().isBadRequest());

        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoEquipos() throws Exception {
        // Initialize the database
        tipoEquipoRepository.saveAndFlush(tipoEquipo);

        // Get all the tipoEquipoList
        restTipoEquipoMockMvc.perform(get("/api/tipo-equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoEquipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoEquipo() throws Exception {
        // Initialize the database
        tipoEquipoRepository.saveAndFlush(tipoEquipo);

        // Get the tipoEquipo
        restTipoEquipoMockMvc.perform(get("/api/tipo-equipos/{id}", tipoEquipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoEquipo.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoEquipo() throws Exception {
        // Get the tipoEquipo
        restTipoEquipoMockMvc.perform(get("/api/tipo-equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoEquipo() throws Exception {
        // Initialize the database
        tipoEquipoRepository.saveAndFlush(tipoEquipo);

        int databaseSizeBeforeUpdate = tipoEquipoRepository.findAll().size();

        // Update the tipoEquipo
        TipoEquipo updatedTipoEquipo = tipoEquipoRepository.findById(tipoEquipo.getId()).get();
        // Disconnect from session so that the updates on updatedTipoEquipo are not directly saved in db
        em.detach(updatedTipoEquipo);
        updatedTipoEquipo
            .nombre(UPDATED_NOMBRE)
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);

        restTipoEquipoMockMvc.perform(put("/api/tipo-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoEquipo)))
            .andExpect(status().isOk());

        // Validate the TipoEquipo in the database
        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeUpdate);
        TipoEquipo testTipoEquipo = tipoEquipoList.get(tipoEquipoList.size() - 1);
        assertThat(testTipoEquipo.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoEquipo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoEquipo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoEquipo() throws Exception {
        int databaseSizeBeforeUpdate = tipoEquipoRepository.findAll().size();

        // Create the TipoEquipo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoEquipoMockMvc.perform(put("/api/tipo-equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoEquipo)))
            .andExpect(status().isBadRequest());

        // Validate the TipoEquipo in the database
        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoEquipo() throws Exception {
        // Initialize the database
        tipoEquipoRepository.saveAndFlush(tipoEquipo);

        int databaseSizeBeforeDelete = tipoEquipoRepository.findAll().size();

        // Delete the tipoEquipo
        restTipoEquipoMockMvc.perform(delete("/api/tipo-equipos/{id}", tipoEquipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoEquipo> tipoEquipoList = tipoEquipoRepository.findAll();
        assertThat(tipoEquipoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoEquipo.class);
        TipoEquipo tipoEquipo1 = new TipoEquipo();
        tipoEquipo1.setId(1L);
        TipoEquipo tipoEquipo2 = new TipoEquipo();
        tipoEquipo2.setId(tipoEquipo1.getId());
        assertThat(tipoEquipo1).isEqualTo(tipoEquipo2);
        tipoEquipo2.setId(2L);
        assertThat(tipoEquipo1).isNotEqualTo(tipoEquipo2);
        tipoEquipo1.setId(null);
        assertThat(tipoEquipo1).isNotEqualTo(tipoEquipo2);
    }
}
