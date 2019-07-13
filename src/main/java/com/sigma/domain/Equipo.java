package com.sigma.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;

import com.sigma.domain.enumeration.EstadoEquipo;

/**
 * A Equipo.
 */
@Entity
@Table(name = "equipo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Equipo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "codigo_equipo", nullable = false)
    private String codigoEquipo;

    @NotNull
    @Column(name = "serie", nullable = false)
    private String serie;

    @Column(name = "calibrado_desde")
    private LocalDate calibradoDesde;

    @Column(name = "calibrado_hasta")
    private LocalDate calibradoHasta;

    @Column(name = "ruta_doc_calibracion")
    private String rutaDocCalibracion;

    @Column(name = "nombre_doc_calibracion")
    private String nombreDocCalibracion;

    @Lob
    @Column(name = "documento_calibracion")
    private byte[] documentoCalibracion;

    @Column(name = "documento_calibracion_content_type")
    private String documentoCalibracionContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoEquipo estado;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Lob
    @Column(name = "observacion")
    private String observacion;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @Lob
    @Column(name = "test")
    private byte[] test;

    @Column(name = "test_content_type")
    private String testContentType;

    @ManyToOne
    @JsonIgnoreProperties("equipos")
    private Modelo modelo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoEquipo() {
        return codigoEquipo;
    }

    public Equipo codigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
        return this;
    }

    public void setCodigoEquipo(String codigoEquipo) {
        this.codigoEquipo = codigoEquipo;
    }

    public String getSerie() {
        return serie;
    }

    public Equipo serie(String serie) {
        this.serie = serie;
        return this;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public LocalDate getCalibradoDesde() {
        return calibradoDesde;
    }

    public Equipo calibradoDesde(LocalDate calibradoDesde) {
        this.calibradoDesde = calibradoDesde;
        return this;
    }

    public void setCalibradoDesde(LocalDate calibradoDesde) {
        this.calibradoDesde = calibradoDesde;
    }

    public LocalDate getCalibradoHasta() {
        return calibradoHasta;
    }

    public Equipo calibradoHasta(LocalDate calibradoHasta) {
        this.calibradoHasta = calibradoHasta;
        return this;
    }

    public void setCalibradoHasta(LocalDate calibradoHasta) {
        this.calibradoHasta = calibradoHasta;
    }

    public String getRutaDocCalibracion() {
        return rutaDocCalibracion;
    }

    public Equipo rutaDocCalibracion(String rutaDocCalibracion) {
        this.rutaDocCalibracion = rutaDocCalibracion;
        return this;
    }

    public void setRutaDocCalibracion(String rutaDocCalibracion) {
        this.rutaDocCalibracion = rutaDocCalibracion;
    }

    public String getNombreDocCalibracion() {
        return nombreDocCalibracion;
    }

    public Equipo nombreDocCalibracion(String nombreDocCalibracion) {
        this.nombreDocCalibracion = nombreDocCalibracion;
        return this;
    }

    public void setNombreDocCalibracion(String nombreDocCalibracion) {
        this.nombreDocCalibracion = nombreDocCalibracion;
    }

    public byte[] getDocumentoCalibracion() {
        return documentoCalibracion;
    }

    public Equipo documentoCalibracion(byte[] documentoCalibracion) {
        this.documentoCalibracion = documentoCalibracion;
        return this;
    }

    public void setDocumentoCalibracion(byte[] documentoCalibracion) {
        this.documentoCalibracion = documentoCalibracion;
    }

    public String getDocumentoCalibracionContentType() {
        return documentoCalibracionContentType;
    }

    public Equipo documentoCalibracionContentType(String documentoCalibracionContentType) {
        this.documentoCalibracionContentType = documentoCalibracionContentType;
        return this;
    }

    public void setDocumentoCalibracionContentType(String documentoCalibracionContentType) {
        this.documentoCalibracionContentType = documentoCalibracionContentType;
    }

    public EstadoEquipo getEstado() {
        return estado;
    }

    public Equipo estado(EstadoEquipo estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(EstadoEquipo estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public Equipo fechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
        return this;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getObservacion() {
        return observacion;
    }

    public Equipo observacion(String observacion) {
        this.observacion = observacion;
        return this;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getComentario() {
        return comentario;
    }

    public Equipo comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public byte[] getTest() {
        return test;
    }

    public Equipo test(byte[] test) {
        this.test = test;
        return this;
    }

    public void setTest(byte[] test) {
        this.test = test;
    }

    public String getTestContentType() {
        return testContentType;
    }

    public Equipo testContentType(String testContentType) {
        this.testContentType = testContentType;
        return this;
    }

    public void setTestContentType(String testContentType) {
        this.testContentType = testContentType;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public Equipo modelo(Modelo modelo) {
        this.modelo = modelo;
        return this;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipo)) {
            return false;
        }
        return id != null && id.equals(((Equipo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Equipo{" +
            "id=" + getId() +
            ", codigoEquipo='" + getCodigoEquipo() + "'" +
            ", serie='" + getSerie() + "'" +
            ", calibradoDesde='" + getCalibradoDesde() + "'" +
            ", calibradoHasta='" + getCalibradoHasta() + "'" +
            ", rutaDocCalibracion='" + getRutaDocCalibracion() + "'" +
            ", nombreDocCalibracion='" + getNombreDocCalibracion() + "'" +
            ", documentoCalibracion='" + getDocumentoCalibracion() + "'" +
            ", documentoCalibracionContentType='" + getDocumentoCalibracionContentType() + "'" +
            ", estado='" + getEstado() + "'" +
            ", fechaCompra='" + getFechaCompra() + "'" +
            ", observacion='" + getObservacion() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", test='" + getTest() + "'" +
            ", testContentType='" + getTestContentType() + "'" +
            "}";
    }
}
