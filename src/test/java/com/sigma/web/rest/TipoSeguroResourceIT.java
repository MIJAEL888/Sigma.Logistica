package com.sigma.web.rest;

import com.sigma.LogisticaApp;
import com.sigma.domain.TipoSeguro;
import com.sigma.repository.TipoSeguroRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.sigma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link TipoSeguroResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class TipoSeguroResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CAUDICIDAD = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CAUDICIDAD = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CODIGO_TIPO_SEGURO = 1;
    private static final Integer UPDATED_CODIGO_TIPO_SEGURO = 2;

    @Autowired
    private TipoSeguroRepository tipoSeguroRepository;

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

    private MockMvc restTipoSeguroMockMvc;

    private TipoSeguro tipoSeguro;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoSeguroResource tipoSeguroResource = new TipoSeguroResource(tipoSeguroRepository);
        this.restTipoSeguroMockMvc = MockMvcBuilders.standaloneSetup(tipoSeguroResource)
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
    public static TipoSeguro createEntity(EntityManager em) {
        TipoSeguro tipoSeguro = new TipoSeguro()
            .nombre(DEFAULT_NOMBRE)
            .descripcion(DEFAULT_DESCRIPCION)
            .fechaCaudicidad(DEFAULT_FECHA_CAUDICIDAD)
            .codigoTipoSeguro(DEFAULT_CODIGO_TIPO_SEGURO);
        return tipoSeguro;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoSeguro createUpdatedEntity(EntityManager em) {
        TipoSeguro tipoSeguro = new TipoSeguro()
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCaudicidad(UPDATED_FECHA_CAUDICIDAD)
            .codigoTipoSeguro(UPDATED_CODIGO_TIPO_SEGURO);
        return tipoSeguro;
    }

    @BeforeEach
    public void initTest() {
        tipoSeguro = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoSeguro() throws Exception {
        int databaseSizeBeforeCreate = tipoSeguroRepository.findAll().size();

        // Create the TipoSeguro
        restTipoSeguroMockMvc.perform(post("/api/tipo-seguros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSeguro)))
            .andExpect(status().isCreated());

        // Validate the TipoSeguro in the database
        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeCreate + 1);
        TipoSeguro testTipoSeguro = tipoSeguroList.get(tipoSeguroList.size() - 1);
        assertThat(testTipoSeguro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoSeguro.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTipoSeguro.getFechaCaudicidad()).isEqualTo(DEFAULT_FECHA_CAUDICIDAD);
        assertThat(testTipoSeguro.getCodigoTipoSeguro()).isEqualTo(DEFAULT_CODIGO_TIPO_SEGURO);
    }

    @Test
    @Transactional
    public void createTipoSeguroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoSeguroRepository.findAll().size();

        // Create the TipoSeguro with an existing ID
        tipoSeguro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoSeguroMockMvc.perform(post("/api/tipo-seguros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSeguro)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSeguro in the database
        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoSeguroRepository.findAll().size();
        // set the field null
        tipoSeguro.setNombre(null);

        // Create the TipoSeguro, which fails.

        restTipoSeguroMockMvc.perform(post("/api/tipo-seguros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSeguro)))
            .andExpect(status().isBadRequest());

        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoSeguros() throws Exception {
        // Initialize the database
        tipoSeguroRepository.saveAndFlush(tipoSeguro);

        // Get all the tipoSeguroList
        restTipoSeguroMockMvc.perform(get("/api/tipo-seguros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoSeguro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].fechaCaudicidad").value(hasItem(DEFAULT_FECHA_CAUDICIDAD.toString())))
            .andExpect(jsonPath("$.[*].codigoTipoSeguro").value(hasItem(DEFAULT_CODIGO_TIPO_SEGURO)));
    }
    
    @Test
    @Transactional
    public void getTipoSeguro() throws Exception {
        // Initialize the database
        tipoSeguroRepository.saveAndFlush(tipoSeguro);

        // Get the tipoSeguro
        restTipoSeguroMockMvc.perform(get("/api/tipo-seguros/{id}", tipoSeguro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoSeguro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.fechaCaudicidad").value(DEFAULT_FECHA_CAUDICIDAD.toString()))
            .andExpect(jsonPath("$.codigoTipoSeguro").value(DEFAULT_CODIGO_TIPO_SEGURO));
    }

    @Test
    @Transactional
    public void getNonExistingTipoSeguro() throws Exception {
        // Get the tipoSeguro
        restTipoSeguroMockMvc.perform(get("/api/tipo-seguros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoSeguro() throws Exception {
        // Initialize the database
        tipoSeguroRepository.saveAndFlush(tipoSeguro);

        int databaseSizeBeforeUpdate = tipoSeguroRepository.findAll().size();

        // Update the tipoSeguro
        TipoSeguro updatedTipoSeguro = tipoSeguroRepository.findById(tipoSeguro.getId()).get();
        // Disconnect from session so that the updates on updatedTipoSeguro are not directly saved in db
        em.detach(updatedTipoSeguro);
        updatedTipoSeguro
            .nombre(UPDATED_NOMBRE)
            .descripcion(UPDATED_DESCRIPCION)
            .fechaCaudicidad(UPDATED_FECHA_CAUDICIDAD)
            .codigoTipoSeguro(UPDATED_CODIGO_TIPO_SEGURO);

        restTipoSeguroMockMvc.perform(put("/api/tipo-seguros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoSeguro)))
            .andExpect(status().isOk());

        // Validate the TipoSeguro in the database
        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeUpdate);
        TipoSeguro testTipoSeguro = tipoSeguroList.get(tipoSeguroList.size() - 1);
        assertThat(testTipoSeguro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoSeguro.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTipoSeguro.getFechaCaudicidad()).isEqualTo(UPDATED_FECHA_CAUDICIDAD);
        assertThat(testTipoSeguro.getCodigoTipoSeguro()).isEqualTo(UPDATED_CODIGO_TIPO_SEGURO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoSeguro() throws Exception {
        int databaseSizeBeforeUpdate = tipoSeguroRepository.findAll().size();

        // Create the TipoSeguro

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoSeguroMockMvc.perform(put("/api/tipo-seguros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoSeguro)))
            .andExpect(status().isBadRequest());

        // Validate the TipoSeguro in the database
        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoSeguro() throws Exception {
        // Initialize the database
        tipoSeguroRepository.saveAndFlush(tipoSeguro);

        int databaseSizeBeforeDelete = tipoSeguroRepository.findAll().size();

        // Delete the tipoSeguro
        restTipoSeguroMockMvc.perform(delete("/api/tipo-seguros/{id}", tipoSeguro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoSeguro> tipoSeguroList = tipoSeguroRepository.findAll();
        assertThat(tipoSeguroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoSeguro.class);
        TipoSeguro tipoSeguro1 = new TipoSeguro();
        tipoSeguro1.setId(1L);
        TipoSeguro tipoSeguro2 = new TipoSeguro();
        tipoSeguro2.setId(tipoSeguro1.getId());
        assertThat(tipoSeguro1).isEqualTo(tipoSeguro2);
        tipoSeguro2.setId(2L);
        assertThat(tipoSeguro1).isNotEqualTo(tipoSeguro2);
        tipoSeguro1.setId(null);
        assertThat(tipoSeguro1).isNotEqualTo(tipoSeguro2);
    }
}
