package com.sigma.web.rest;

import com.sigma.LogisticaApp;
import com.sigma.domain.Laboratorio;
import com.sigma.repository.LaboratorioRepository;
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
 * Integration tests for the {@Link LaboratorioResource} REST controller.
 */
@SpringBootTest(classes = LogisticaApp.class)
public class LaboratorioResourceIT {

    private static final String DEFAULT_RAZON_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZON_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_RUC = "AAAAAAAAAA";
    private static final String UPDATED_RUC = "BBBBBBBBBB";

    private static final String DEFAULT_ACREDITADO_POR = "AAAAAAAAAA";
    private static final String UPDATED_ACREDITADO_POR = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO_ACREDITACION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_ACREDITACION = "BBBBBBBBBB";

    private static final String DEFAULT_RUTA_DOC_ACREDITACION = "AAAAAAAAAA";
    private static final String UPDATED_RUTA_DOC_ACREDITACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_DOC_ACREDITACION = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_DOC_ACREDITACION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_DOCUMENTO_ACREDITACION = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DOCUMENTO_ACREDITACION = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DOCUMENTO_ACREDITACION_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DOCUMENTO_ACREDITACION_CONTENT_TYPE = "image/png";

    private static final LocalDate DEFAULT_VIGENCIA_DESDE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIGENCIA_DESDE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_VIGENCIA_HASTA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VIGENCIA_HASTA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_CORREO = "AAAAAAAAAA";
    private static final String UPDATED_CORREO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CONTACTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CONTACTO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FECHA_CREACION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_CREACION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private LaboratorioRepository laboratorioRepository;

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

    private MockMvc restLaboratorioMockMvc;

    private Laboratorio laboratorio;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LaboratorioResource laboratorioResource = new LaboratorioResource(laboratorioRepository);
        this.restLaboratorioMockMvc = MockMvcBuilders.standaloneSetup(laboratorioResource)
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
    public static Laboratorio createEntity(EntityManager em) {
        Laboratorio laboratorio = new Laboratorio()
            .razonSocial(DEFAULT_RAZON_SOCIAL)
            .direccion(DEFAULT_DIRECCION)
            .ruc(DEFAULT_RUC)
            .acreditadoPor(DEFAULT_ACREDITADO_POR)
            .numeroAcreditacion(DEFAULT_NUMERO_ACREDITACION)
            .rutaDocAcreditacion(DEFAULT_RUTA_DOC_ACREDITACION)
            .nombreDocAcreditacion(DEFAULT_NOMBRE_DOC_ACREDITACION)
            .documentoAcreditacion(DEFAULT_DOCUMENTO_ACREDITACION)
            .documentoAcreditacionContentType(DEFAULT_DOCUMENTO_ACREDITACION_CONTENT_TYPE)
            .vigenciaDesde(DEFAULT_VIGENCIA_DESDE)
            .vigenciaHasta(DEFAULT_VIGENCIA_HASTA)
            .telefono(DEFAULT_TELEFONO)
            .correo(DEFAULT_CORREO)
            .nombreContacto(DEFAULT_NOMBRE_CONTACTO)
            .fechaCreacion(DEFAULT_FECHA_CREACION);
        return laboratorio;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Laboratorio createUpdatedEntity(EntityManager em) {
        Laboratorio laboratorio = new Laboratorio()
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .direccion(UPDATED_DIRECCION)
            .ruc(UPDATED_RUC)
            .acreditadoPor(UPDATED_ACREDITADO_POR)
            .numeroAcreditacion(UPDATED_NUMERO_ACREDITACION)
            .rutaDocAcreditacion(UPDATED_RUTA_DOC_ACREDITACION)
            .nombreDocAcreditacion(UPDATED_NOMBRE_DOC_ACREDITACION)
            .documentoAcreditacion(UPDATED_DOCUMENTO_ACREDITACION)
            .documentoAcreditacionContentType(UPDATED_DOCUMENTO_ACREDITACION_CONTENT_TYPE)
            .vigenciaDesde(UPDATED_VIGENCIA_DESDE)
            .vigenciaHasta(UPDATED_VIGENCIA_HASTA)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .fechaCreacion(UPDATED_FECHA_CREACION);
        return laboratorio;
    }

    @BeforeEach
    public void initTest() {
        laboratorio = createEntity(em);
    }

    @Test
    @Transactional
    public void createLaboratorio() throws Exception {
        int databaseSizeBeforeCreate = laboratorioRepository.findAll().size();

        // Create the Laboratorio
        restLaboratorioMockMvc.perform(post("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isCreated());

        // Validate the Laboratorio in the database
        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeCreate + 1);
        Laboratorio testLaboratorio = laboratorioList.get(laboratorioList.size() - 1);
        assertThat(testLaboratorio.getRazonSocial()).isEqualTo(DEFAULT_RAZON_SOCIAL);
        assertThat(testLaboratorio.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testLaboratorio.getRuc()).isEqualTo(DEFAULT_RUC);
        assertThat(testLaboratorio.getAcreditadoPor()).isEqualTo(DEFAULT_ACREDITADO_POR);
        assertThat(testLaboratorio.getNumeroAcreditacion()).isEqualTo(DEFAULT_NUMERO_ACREDITACION);
        assertThat(testLaboratorio.getRutaDocAcreditacion()).isEqualTo(DEFAULT_RUTA_DOC_ACREDITACION);
        assertThat(testLaboratorio.getNombreDocAcreditacion()).isEqualTo(DEFAULT_NOMBRE_DOC_ACREDITACION);
        assertThat(testLaboratorio.getDocumentoAcreditacion()).isEqualTo(DEFAULT_DOCUMENTO_ACREDITACION);
        assertThat(testLaboratorio.getDocumentoAcreditacionContentType()).isEqualTo(DEFAULT_DOCUMENTO_ACREDITACION_CONTENT_TYPE);
        assertThat(testLaboratorio.getVigenciaDesde()).isEqualTo(DEFAULT_VIGENCIA_DESDE);
        assertThat(testLaboratorio.getVigenciaHasta()).isEqualTo(DEFAULT_VIGENCIA_HASTA);
        assertThat(testLaboratorio.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testLaboratorio.getCorreo()).isEqualTo(DEFAULT_CORREO);
        assertThat(testLaboratorio.getNombreContacto()).isEqualTo(DEFAULT_NOMBRE_CONTACTO);
        assertThat(testLaboratorio.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void createLaboratorioWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = laboratorioRepository.findAll().size();

        // Create the Laboratorio with an existing ID
        laboratorio.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLaboratorioMockMvc.perform(post("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratorio in the database
        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkRazonSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioRepository.findAll().size();
        // set the field null
        laboratorio.setRazonSocial(null);

        // Create the Laboratorio, which fails.

        restLaboratorioMockMvc.perform(post("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isBadRequest());

        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDireccionIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioRepository.findAll().size();
        // set the field null
        laboratorio.setDireccion(null);

        // Create the Laboratorio, which fails.

        restLaboratorioMockMvc.perform(post("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isBadRequest());

        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRucIsRequired() throws Exception {
        int databaseSizeBeforeTest = laboratorioRepository.findAll().size();
        // set the field null
        laboratorio.setRuc(null);

        // Create the Laboratorio, which fails.

        restLaboratorioMockMvc.perform(post("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isBadRequest());

        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLaboratorios() throws Exception {
        // Initialize the database
        laboratorioRepository.saveAndFlush(laboratorio);

        // Get all the laboratorioList
        restLaboratorioMockMvc.perform(get("/api/laboratorios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(laboratorio.getId().intValue())))
            .andExpect(jsonPath("$.[*].razonSocial").value(hasItem(DEFAULT_RAZON_SOCIAL.toString())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
            .andExpect(jsonPath("$.[*].ruc").value(hasItem(DEFAULT_RUC.toString())))
            .andExpect(jsonPath("$.[*].acreditadoPor").value(hasItem(DEFAULT_ACREDITADO_POR.toString())))
            .andExpect(jsonPath("$.[*].numeroAcreditacion").value(hasItem(DEFAULT_NUMERO_ACREDITACION.toString())))
            .andExpect(jsonPath("$.[*].rutaDocAcreditacion").value(hasItem(DEFAULT_RUTA_DOC_ACREDITACION.toString())))
            .andExpect(jsonPath("$.[*].nombreDocAcreditacion").value(hasItem(DEFAULT_NOMBRE_DOC_ACREDITACION.toString())))
            .andExpect(jsonPath("$.[*].documentoAcreditacionContentType").value(hasItem(DEFAULT_DOCUMENTO_ACREDITACION_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].documentoAcreditacion").value(hasItem(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_ACREDITACION))))
            .andExpect(jsonPath("$.[*].vigenciaDesde").value(hasItem(DEFAULT_VIGENCIA_DESDE.toString())))
            .andExpect(jsonPath("$.[*].vigenciaHasta").value(hasItem(DEFAULT_VIGENCIA_HASTA.toString())))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())))
            .andExpect(jsonPath("$.[*].correo").value(hasItem(DEFAULT_CORREO.toString())))
            .andExpect(jsonPath("$.[*].nombreContacto").value(hasItem(DEFAULT_NOMBRE_CONTACTO.toString())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())));
    }
    
    @Test
    @Transactional
    public void getLaboratorio() throws Exception {
        // Initialize the database
        laboratorioRepository.saveAndFlush(laboratorio);

        // Get the laboratorio
        restLaboratorioMockMvc.perform(get("/api/laboratorios/{id}", laboratorio.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(laboratorio.getId().intValue()))
            .andExpect(jsonPath("$.razonSocial").value(DEFAULT_RAZON_SOCIAL.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.ruc").value(DEFAULT_RUC.toString()))
            .andExpect(jsonPath("$.acreditadoPor").value(DEFAULT_ACREDITADO_POR.toString()))
            .andExpect(jsonPath("$.numeroAcreditacion").value(DEFAULT_NUMERO_ACREDITACION.toString()))
            .andExpect(jsonPath("$.rutaDocAcreditacion").value(DEFAULT_RUTA_DOC_ACREDITACION.toString()))
            .andExpect(jsonPath("$.nombreDocAcreditacion").value(DEFAULT_NOMBRE_DOC_ACREDITACION.toString()))
            .andExpect(jsonPath("$.documentoAcreditacionContentType").value(DEFAULT_DOCUMENTO_ACREDITACION_CONTENT_TYPE))
            .andExpect(jsonPath("$.documentoAcreditacion").value(Base64Utils.encodeToString(DEFAULT_DOCUMENTO_ACREDITACION)))
            .andExpect(jsonPath("$.vigenciaDesde").value(DEFAULT_VIGENCIA_DESDE.toString()))
            .andExpect(jsonPath("$.vigenciaHasta").value(DEFAULT_VIGENCIA_HASTA.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()))
            .andExpect(jsonPath("$.correo").value(DEFAULT_CORREO.toString()))
            .andExpect(jsonPath("$.nombreContacto").value(DEFAULT_NOMBRE_CONTACTO.toString()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLaboratorio() throws Exception {
        // Get the laboratorio
        restLaboratorioMockMvc.perform(get("/api/laboratorios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLaboratorio() throws Exception {
        // Initialize the database
        laboratorioRepository.saveAndFlush(laboratorio);

        int databaseSizeBeforeUpdate = laboratorioRepository.findAll().size();

        // Update the laboratorio
        Laboratorio updatedLaboratorio = laboratorioRepository.findById(laboratorio.getId()).get();
        // Disconnect from session so that the updates on updatedLaboratorio are not directly saved in db
        em.detach(updatedLaboratorio);
        updatedLaboratorio
            .razonSocial(UPDATED_RAZON_SOCIAL)
            .direccion(UPDATED_DIRECCION)
            .ruc(UPDATED_RUC)
            .acreditadoPor(UPDATED_ACREDITADO_POR)
            .numeroAcreditacion(UPDATED_NUMERO_ACREDITACION)
            .rutaDocAcreditacion(UPDATED_RUTA_DOC_ACREDITACION)
            .nombreDocAcreditacion(UPDATED_NOMBRE_DOC_ACREDITACION)
            .documentoAcreditacion(UPDATED_DOCUMENTO_ACREDITACION)
            .documentoAcreditacionContentType(UPDATED_DOCUMENTO_ACREDITACION_CONTENT_TYPE)
            .vigenciaDesde(UPDATED_VIGENCIA_DESDE)
            .vigenciaHasta(UPDATED_VIGENCIA_HASTA)
            .telefono(UPDATED_TELEFONO)
            .correo(UPDATED_CORREO)
            .nombreContacto(UPDATED_NOMBRE_CONTACTO)
            .fechaCreacion(UPDATED_FECHA_CREACION);

        restLaboratorioMockMvc.perform(put("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLaboratorio)))
            .andExpect(status().isOk());

        // Validate the Laboratorio in the database
        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeUpdate);
        Laboratorio testLaboratorio = laboratorioList.get(laboratorioList.size() - 1);
        assertThat(testLaboratorio.getRazonSocial()).isEqualTo(UPDATED_RAZON_SOCIAL);
        assertThat(testLaboratorio.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testLaboratorio.getRuc()).isEqualTo(UPDATED_RUC);
        assertThat(testLaboratorio.getAcreditadoPor()).isEqualTo(UPDATED_ACREDITADO_POR);
        assertThat(testLaboratorio.getNumeroAcreditacion()).isEqualTo(UPDATED_NUMERO_ACREDITACION);
        assertThat(testLaboratorio.getRutaDocAcreditacion()).isEqualTo(UPDATED_RUTA_DOC_ACREDITACION);
        assertThat(testLaboratorio.getNombreDocAcreditacion()).isEqualTo(UPDATED_NOMBRE_DOC_ACREDITACION);
        assertThat(testLaboratorio.getDocumentoAcreditacion()).isEqualTo(UPDATED_DOCUMENTO_ACREDITACION);
        assertThat(testLaboratorio.getDocumentoAcreditacionContentType()).isEqualTo(UPDATED_DOCUMENTO_ACREDITACION_CONTENT_TYPE);
        assertThat(testLaboratorio.getVigenciaDesde()).isEqualTo(UPDATED_VIGENCIA_DESDE);
        assertThat(testLaboratorio.getVigenciaHasta()).isEqualTo(UPDATED_VIGENCIA_HASTA);
        assertThat(testLaboratorio.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testLaboratorio.getCorreo()).isEqualTo(UPDATED_CORREO);
        assertThat(testLaboratorio.getNombreContacto()).isEqualTo(UPDATED_NOMBRE_CONTACTO);
        assertThat(testLaboratorio.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
    }

    @Test
    @Transactional
    public void updateNonExistingLaboratorio() throws Exception {
        int databaseSizeBeforeUpdate = laboratorioRepository.findAll().size();

        // Create the Laboratorio

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLaboratorioMockMvc.perform(put("/api/laboratorios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(laboratorio)))
            .andExpect(status().isBadRequest());

        // Validate the Laboratorio in the database
        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLaboratorio() throws Exception {
        // Initialize the database
        laboratorioRepository.saveAndFlush(laboratorio);

        int databaseSizeBeforeDelete = laboratorioRepository.findAll().size();

        // Delete the laboratorio
        restLaboratorioMockMvc.perform(delete("/api/laboratorios/{id}", laboratorio.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Laboratorio> laboratorioList = laboratorioRepository.findAll();
        assertThat(laboratorioList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Laboratorio.class);
        Laboratorio laboratorio1 = new Laboratorio();
        laboratorio1.setId(1L);
        Laboratorio laboratorio2 = new Laboratorio();
        laboratorio2.setId(laboratorio1.getId());
        assertThat(laboratorio1).isEqualTo(laboratorio2);
        laboratorio2.setId(2L);
        assertThat(laboratorio1).isNotEqualTo(laboratorio2);
        laboratorio1.setId(null);
        assertThat(laboratorio1).isNotEqualTo(laboratorio2);
    }
}
