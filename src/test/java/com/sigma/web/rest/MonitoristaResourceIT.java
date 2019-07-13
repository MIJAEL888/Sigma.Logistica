package com.sigma.web.rest;

import com.sigma.LogisticaApp;
import com.sigma.domain.Monitorista;
import com.sigma.repository.MonitoristaRepository;
import com.sigma.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.sigma.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link MonitoristaResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class MonitoristaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_PATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_PATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDO_MATERNO = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDO_MATERNO = "BBBBBBBBBB";

    private static final String DEFAULT_DNI = "AAAAAAAAAA";
    private static final String UPDATED_DNI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_NACIMIENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_NACIMIENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private MonitoristaRepository monitoristaRepository;

    @Mock
    private MonitoristaRepository monitoristaRepositoryMock;

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

    private MockMvc restMonitoristaMockMvc;

    private Monitorista monitorista;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MonitoristaResource monitoristaResource = new MonitoristaResource(monitoristaRepository);
        this.restMonitoristaMockMvc = MockMvcBuilders.standaloneSetup(monitoristaResource)
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
    public static Monitorista createEntity(EntityManager em) {
        Monitorista monitorista = new Monitorista()
            .nombre(DEFAULT_NOMBRE)
            .apellidoPaterno(DEFAULT_APELLIDO_PATERNO)
            .apellidoMaterno(DEFAULT_APELLIDO_MATERNO)
            .dni(DEFAULT_DNI)
            .fechaNacimiento(DEFAULT_FECHA_NACIMIENTO);
        return monitorista;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Monitorista createUpdatedEntity(EntityManager em) {
        Monitorista monitorista = new Monitorista()
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);
        return monitorista;
    }

    @BeforeEach
    public void initTest() {
        monitorista = createEntity(em);
    }

    @Test
    @Transactional
    public void createMonitorista() throws Exception {
        int databaseSizeBeforeCreate = monitoristaRepository.findAll().size();

        // Create the Monitorista
        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isCreated());

        // Validate the Monitorista in the database
        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeCreate + 1);
        Monitorista testMonitorista = monitoristaList.get(monitoristaList.size() - 1);
        assertThat(testMonitorista.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testMonitorista.getApellidoPaterno()).isEqualTo(DEFAULT_APELLIDO_PATERNO);
        assertThat(testMonitorista.getApellidoMaterno()).isEqualTo(DEFAULT_APELLIDO_MATERNO);
        assertThat(testMonitorista.getDni()).isEqualTo(DEFAULT_DNI);
        assertThat(testMonitorista.getFechaNacimiento()).isEqualTo(DEFAULT_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void createMonitoristaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = monitoristaRepository.findAll().size();

        // Create the Monitorista with an existing ID
        monitorista.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        // Validate the Monitorista in the database
        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = monitoristaRepository.findAll().size();
        // set the field null
        monitorista.setNombre(null);

        // Create the Monitorista, which fails.

        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoPaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = monitoristaRepository.findAll().size();
        // set the field null
        monitorista.setApellidoPaterno(null);

        // Create the Monitorista, which fails.

        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidoMaternoIsRequired() throws Exception {
        int databaseSizeBeforeTest = monitoristaRepository.findAll().size();
        // set the field null
        monitorista.setApellidoMaterno(null);

        // Create the Monitorista, which fails.

        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDniIsRequired() throws Exception {
        int databaseSizeBeforeTest = monitoristaRepository.findAll().size();
        // set the field null
        monitorista.setDni(null);

        // Create the Monitorista, which fails.

        restMonitoristaMockMvc.perform(post("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMonitoristas() throws Exception {
        // Initialize the database
        monitoristaRepository.saveAndFlush(monitorista);

        // Get all the monitoristaList
        restMonitoristaMockMvc.perform(get("/api/monitoristas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(monitorista.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].apellidoPaterno").value(hasItem(DEFAULT_APELLIDO_PATERNO.toString())))
            .andExpect(jsonPath("$.[*].apellidoMaterno").value(hasItem(DEFAULT_APELLIDO_MATERNO.toString())))
            .andExpect(jsonPath("$.[*].dni").value(hasItem(DEFAULT_DNI.toString())))
            .andExpect(jsonPath("$.[*].fechaNacimiento").value(hasItem(DEFAULT_FECHA_NACIMIENTO.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMonitoristasWithEagerRelationshipsIsEnabled() throws Exception {
        MonitoristaResource monitoristaResource = new MonitoristaResource(monitoristaRepositoryMock);
        when(monitoristaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restMonitoristaMockMvc = MockMvcBuilders.standaloneSetup(monitoristaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMonitoristaMockMvc.perform(get("/api/monitoristas?eagerload=true"))
        .andExpect(status().isOk());

        verify(monitoristaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMonitoristasWithEagerRelationshipsIsNotEnabled() throws Exception {
        MonitoristaResource monitoristaResource = new MonitoristaResource(monitoristaRepositoryMock);
            when(monitoristaRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restMonitoristaMockMvc = MockMvcBuilders.standaloneSetup(monitoristaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restMonitoristaMockMvc.perform(get("/api/monitoristas?eagerload=true"))
        .andExpect(status().isOk());

            verify(monitoristaRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMonitorista() throws Exception {
        // Initialize the database
        monitoristaRepository.saveAndFlush(monitorista);

        // Get the monitorista
        restMonitoristaMockMvc.perform(get("/api/monitoristas/{id}", monitorista.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(monitorista.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.apellidoPaterno").value(DEFAULT_APELLIDO_PATERNO.toString()))
            .andExpect(jsonPath("$.apellidoMaterno").value(DEFAULT_APELLIDO_MATERNO.toString()))
            .andExpect(jsonPath("$.dni").value(DEFAULT_DNI.toString()))
            .andExpect(jsonPath("$.fechaNacimiento").value(DEFAULT_FECHA_NACIMIENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMonitorista() throws Exception {
        // Get the monitorista
        restMonitoristaMockMvc.perform(get("/api/monitoristas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMonitorista() throws Exception {
        // Initialize the database
        monitoristaRepository.saveAndFlush(monitorista);

        int databaseSizeBeforeUpdate = monitoristaRepository.findAll().size();

        // Update the monitorista
        Monitorista updatedMonitorista = monitoristaRepository.findById(monitorista.getId()).get();
        // Disconnect from session so that the updates on updatedMonitorista are not directly saved in db
        em.detach(updatedMonitorista);
        updatedMonitorista
            .nombre(UPDATED_NOMBRE)
            .apellidoPaterno(UPDATED_APELLIDO_PATERNO)
            .apellidoMaterno(UPDATED_APELLIDO_MATERNO)
            .dni(UPDATED_DNI)
            .fechaNacimiento(UPDATED_FECHA_NACIMIENTO);

        restMonitoristaMockMvc.perform(put("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMonitorista)))
            .andExpect(status().isOk());

        // Validate the Monitorista in the database
        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeUpdate);
        Monitorista testMonitorista = monitoristaList.get(monitoristaList.size() - 1);
        assertThat(testMonitorista.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testMonitorista.getApellidoPaterno()).isEqualTo(UPDATED_APELLIDO_PATERNO);
        assertThat(testMonitorista.getApellidoMaterno()).isEqualTo(UPDATED_APELLIDO_MATERNO);
        assertThat(testMonitorista.getDni()).isEqualTo(UPDATED_DNI);
        assertThat(testMonitorista.getFechaNacimiento()).isEqualTo(UPDATED_FECHA_NACIMIENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingMonitorista() throws Exception {
        int databaseSizeBeforeUpdate = monitoristaRepository.findAll().size();

        // Create the Monitorista

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMonitoristaMockMvc.perform(put("/api/monitoristas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(monitorista)))
            .andExpect(status().isBadRequest());

        // Validate the Monitorista in the database
        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMonitorista() throws Exception {
        // Initialize the database
        monitoristaRepository.saveAndFlush(monitorista);

        int databaseSizeBeforeDelete = monitoristaRepository.findAll().size();

        // Delete the monitorista
        restMonitoristaMockMvc.perform(delete("/api/monitoristas/{id}", monitorista.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Monitorista> monitoristaList = monitoristaRepository.findAll();
        assertThat(monitoristaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monitorista.class);
        Monitorista monitorista1 = new Monitorista();
        monitorista1.setId(1L);
        Monitorista monitorista2 = new Monitorista();
        monitorista2.setId(monitorista1.getId());
        assertThat(monitorista1).isEqualTo(monitorista2);
        monitorista2.setId(2L);
        assertThat(monitorista1).isNotEqualTo(monitorista2);
        monitorista1.setId(null);
        assertThat(monitorista1).isNotEqualTo(monitorista2);
    }
}
