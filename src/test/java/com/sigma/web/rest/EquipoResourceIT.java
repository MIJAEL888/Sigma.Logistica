package com.sigma.web.rest;

import com.sigma.LogisticaApp;
import com.sigma.domain.Equipo;
import com.sigma.repository.EquipoRepository;
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

import com.sigma.domain.enumeration.EstadoEquipo;
/**
 * Integration tests for the {@Link EquipoResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class EquipoResourceIT {

    private static final String DEFAULT_CODIGO_EQUIPO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_EQUIPO = "BBBBBBBBBB";

    private static final String DEFAULT_SERIE = "AAAAAAAAAA";
    private static final String UPDATED_SERIE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CALIBRADO_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALIBRADO_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CALIBRADO_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CALIBRADO_HASTA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_RUTA_DOC_CALIBRACION = "AAAAAAAAAA";
    private static final String UPDATED_RUTA_DOC_CALIBRACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DOC_CALIBRACION = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOC_CALIBRACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO_CALIBRACION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO_CALIBRACION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_CALIBRACION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_CALIBRACION_CONTENT_TYPE = "image/png";

    private static final EstadoEquipo DEFAULT_ESTADO = EstadoEquipo.CALIBRADO;
    private static final EstadoEquipo UPDATED_ESTADO = EstadoEquipo.MANTENIMIENTO;

    private static final LocalDate DEFAULT_FECHA_COMPRA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_COMPRA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACION = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACION = "BBBBBBBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TEST = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEST = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TEST_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEST_CONTENT_TYPE = "image/png";

    @Autowired
    private EquipoRepository equipoRepository;

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

    private MockMvc restEquipoMockMvc;

    private Equipo equipo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipoResource equipoResource = new EquipoResource(equipoRepository);
        this.restEquipoMockMvc = MockMvcBuilders.standaloneSetup(equipoResource)
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
    public static Equipo createEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .codigoEquipo(DEFAULT_CODIGO_EQUIPO)
            .serie(DEFAULT_SERIE)
            .calibradoDesde(DEFAULT_CALIBRADO_DESDE)
            .calibradoHasta(DEFAULT_CALIBRADO_HASTA)
            .rutaDocCalibracion(DEFAULT_RUTA_DOC_CALIBRACION)
            .nombreDocCalibracion(DEFAULT_NOMBRE_DOC_CALIBRACION)
            .documentoCalibracion(DEFAULT_DOCUMENTO_CALIBRACION)
            .documentoCalibracionContentType(DEFAULT_DOCUMENTO_CALIBRACION_CONTENT_TYPE)
            .estado(DEFAULT_ESTADO)
            .fechaCompra(DEFAULT_FECHA_COMPRA)
            .observacion(DEFAULT_OBSERVACION)
            .comentario(DEFAULT_COMENTARIO)
            .test(DEFAULT_TEST)
            .testContentType(DEFAULT_TEST_CONTENT_TYPE);
        return equipo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipo createUpdatedEntity(EntityManager em) {
        Equipo equipo = new Equipo()
            .codigoEquipo(UPDATED_CODIGO_EQUIPO)
            .serie(UPDATED_SERIE)
            .calibradoDesde(UPDATED_CALIBRADO_DESDE)
            .calibradoHasta(UPDATED_CALIBRADO_HASTA)
            .rutaDocCalibracion(UPDATED_RUTA_DOC_CALIBRACION)
            .nombreDocCalibracion(UPDATED_NOMBRE_DOC_CALIBRACION)
            .documentoCalibracion(UPDATED_DOCUMENTO_CALIBRACION)
            .documentoCalibracionContentType(UPDATED_DOCUMENTO_CALIBRACION_CONTENT_TYPE)
            .estado(UPDATED_ESTADO)
            .fechaCompra(UPDATED_FECHA_COMPRA)
            .observacion(UPDATED_OBSERVACION)
            .comentario(UPDATED_COMENTARIO)
            .test(UPDATED_TEST)
            .testContentType(UPDATED_TEST_CONTENT_TYPE);
        return equipo;
    }

    @BeforeEach
    public void initTest() {
        equipo = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipo() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isCreated());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate + 1);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getCodigoEquipo()).isEqualTo(DEFAULT_CODIGO_EQUIPO);
        assertThat(testEquipo.getSerie()).isEqualTo(DEFAULT_SERIE);
        assertThat(testEquipo.getCalibradoDesde()).isEqualTo(DEFAULT_CALIBRADO_DESDE);
        assertThat(testEquipo.getCalibradoHasta()).isEqualTo(DEFAULT_CALIBRADO_HASTA);
        assertThat(testEquipo.getRutaDocCalibracion()).isEqualTo(DEFAULT_RUTA_DOC_CALIBRACION);
        assertThat(testEquipo.getNombreDocCalibracion()).isEqualTo(DEFAULT_NOMBRE_DOC_CALIBRACION);
        assertThat(testEquipo.getDocumentoCalibracion()).isEqualTo(DEFAULT_DOCUMENTO_CALIBRACION);
        assertThat(testEquipo.getDocumentoCalibracionContentType()).isEqualTo(DEFAULT_DOCUMENTO_CALIBRACION_CONTENT_TYPE);
        assertThat(testEquipo.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testEquipo.getFechaCompra()).isEqualTo(DEFAULT_FECHA_COMPRA);
        assertThat(testEquipo.getObservacion()).isEqualTo(DEFAULT_OBSERVACION);
        assertThat(testEquipo.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testEquipo.getTest()).isEqualTo(DEFAULT_TEST);
        assertThat(testEquipo.getTestContentType()).isEqualTo(DEFAULT_TEST_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createEquipoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipoRepository.findAll().size();

        // Create the Equipo with an existing ID
        equipo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoEquipoIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setCodigoEquipo(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSerieIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipoRepository.findAll().size();
        // set the field null
        equipo.setSerie(null);

        // Create the Equipo, which fails.

        restEquipoMockMvc.perform(post("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipos() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get all the equipoList
        restEquipoMockMvc.perform(get("/api/equipos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigoEquipo").value(hasItem(DEFAULT_CODIGO_EQUIPO.toString())))
            .andExpect(jsonPath("$.[*].serie").value(hasItem(DEFAULT_SERIE.toString())))
            .andExpect(jsonPath("$.[*].calibradoDesde").value(hasItem(DEFAULT_CALIBRADO_DESDE.toString())))
            .andExpect(jsonPath("$.[*].calibradoHasta").value(hasItem(DEFAULT_CALIBRADO_HASTA.toString())))
            .andExpect(jsonPath("$.[*].rutaDocCalibracion").value(hasItem(DEFAULT_RUTA_DOC_CALIBRACION.toString())))
            .andExpect(jsonPath("$.[*].nombreDocCalibracion").value(hasItem(DEFAULT_NOMBRE_DOC_CALIBRACION.toString())))
            .andExpect(jsonPath("$.[*].documentoCalibracionContentType").value(hasItem(DEFAULT_DOCUMENTO_CALIBRACION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentoCalibracion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_CALIBRACION))))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].fechaCompra").value(hasItem(DEFAULT_FECHA_COMPRA.toString())))
            .andExpect(jsonPath("$.[*].observacion").value(hasItem(DEFAULT_OBSERVACION.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
            .andExpect(jsonPath("$.[*].testContentType").value(hasItem(DEFAULT_TEST_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].test").value(hasItem(Base64Utils.encodeToString(DEFAULT_TEST))));
    }
    
    @Test
    @Transactional
    public void getEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", equipo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipo.getId().intValue()))
            .andExpect(jsonPath("$.codigoEquipo").value(DEFAULT_CODIGO_EQUIPO.toString()))
            .andExpect(jsonPath("$.serie").value(DEFAULT_SERIE.toString()))
            .andExpect(jsonPath("$.calibradoDesde").value(DEFAULT_CALIBRADO_DESDE.toString()))
            .andExpect(jsonPath("$.calibradoHasta").value(DEFAULT_CALIBRADO_HASTA.toString()))
            .andExpect(jsonPath("$.rutaDocCalibracion").value(DEFAULT_RUTA_DOC_CALIBRACION.toString()))
            .andExpect(jsonPath("$.nombreDocCalibracion").value(DEFAULT_NOMBRE_DOC_CALIBRACION.toString()))
            .andExpect(jsonPath("$.documentoCalibracionContentType").value(DEFAULT_DOCUMENTO_CALIBRACION_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentoCalibracion").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_CALIBRACION)))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.fechaCompra").value(DEFAULT_FECHA_COMPRA.toString()))
            .andExpect(jsonPath("$.observacion").value(DEFAULT_OBSERVACION.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.testContentType").value(DEFAULT_TEST_CONTENT_TYPE))
            .andExpect(jsonPath("$.test").value(Base64Utils.encodeToString(DEFAULT_TEST)));
    }

    @Test
    @Transactional
    public void getNonExistingEquipo() throws Exception {
        // Get the equipo
        restEquipoMockMvc.perform(get("/api/equipos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Update the equipo
        Equipo updatedEquipo = equipoRepository.findById(equipo.getId()).get();
        // Disconnect from session so that the updates on updatedEquipo are not directly saved in db
        em.detach(updatedEquipo);
        updatedEquipo
            .codigoEquipo(UPDATED_CODIGO_EQUIPO)
            .serie(UPDATED_SERIE)
            .calibradoDesde(UPDATED_CALIBRADO_DESDE)
            .calibradoHasta(UPDATED_CALIBRADO_HASTA)
            .rutaDocCalibracion(UPDATED_RUTA_DOC_CALIBRACION)
            .nombreDocCalibracion(UPDATED_NOMBRE_DOC_CALIBRACION)
            .documentoCalibracion(UPDATED_DOCUMENTO_CALIBRACION)
            .documentoCalibracionContentType(UPDATED_DOCUMENTO_CALIBRACION_CONTENT_TYPE)
            .estado(UPDATED_ESTADO)
            .fechaCompra(UPDATED_FECHA_COMPRA)
            .observacion(UPDATED_OBSERVACION)
            .comentario(UPDATED_COMENTARIO)
            .test(UPDATED_TEST)
            .testContentType(UPDATED_TEST_CONTENT_TYPE);

        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipo)))
            .andExpect(status().isOk());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
        Equipo testEquipo = equipoList.get(equipoList.size() - 1);
        assertThat(testEquipo.getCodigoEquipo()).isEqualTo(UPDATED_CODIGO_EQUIPO);
        assertThat(testEquipo.getSerie()).isEqualTo(UPDATED_SERIE);
        assertThat(testEquipo.getCalibradoDesde()).isEqualTo(UPDATED_CALIBRADO_DESDE);
        assertThat(testEquipo.getCalibradoHasta()).isEqualTo(UPDATED_CALIBRADO_HASTA);
        assertThat(testEquipo.getRutaDocCalibracion()).isEqualTo(UPDATED_RUTA_DOC_CALIBRACION);
        assertThat(testEquipo.getNombreDocCalibracion()).isEqualTo(UPDATED_NOMBRE_DOC_CALIBRACION);
        assertThat(testEquipo.getDocumentoCalibracion()).isEqualTo(UPDATED_DOCUMENTO_CALIBRACION);
        assertThat(testEquipo.getDocumentoCalibracionContentType()).isEqualTo(UPDATED_DOCUMENTO_CALIBRACION_CONTENT_TYPE);
        assertThat(testEquipo.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testEquipo.getFechaCompra()).isEqualTo(UPDATED_FECHA_COMPRA);
        assertThat(testEquipo.getObservacion()).isEqualTo(UPDATED_OBSERVACION);
        assertThat(testEquipo.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testEquipo.getTest()).isEqualTo(UPDATED_TEST);
        assertThat(testEquipo.getTestContentType()).isEqualTo(UPDATED_TEST_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipo() throws Exception {
        int databaseSizeBeforeUpdate = equipoRepository.findAll().size();

        // Create the Equipo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipoMockMvc.perform(put("/api/equipos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipo)))
            .andExpect(status().isBadRequest());

        // Validate the Equipo in the database
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEquipo() throws Exception {
        // Initialize the database
        equipoRepository.saveAndFlush(equipo);

        int databaseSizeBeforeDelete = equipoRepository.findAll().size();

        // Delete the equipo
        restEquipoMockMvc.perform(delete("/api/equipos/{id}", equipo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipo> equipoList = equipoRepository.findAll();
        assertThat(equipoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipo.class);
        Equipo equipo1 = new Equipo();
        equipo1.setId(1L);
        Equipo equipo2 = new Equipo();
        equipo2.setId(equipo1.getId());
        assertThat(equipo1).isEqualTo(equipo2);
        equipo2.setId(2L);
        assertThat(equipo1).isNotEqualTo(equipo2);
        equipo1.setId(null);
        assertThat(equipo1).isNotEqualTo(equipo2);
    }
}
