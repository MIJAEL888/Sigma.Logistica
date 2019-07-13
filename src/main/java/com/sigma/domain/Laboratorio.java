package com.sigma.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Laboratorio.
 */
@Entity
@Table(name = "laboratorio")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Laboratorio implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @NotNull
    @Column(name = "direccion", nullable = false)
    private String direccion;

    @NotNull
    @Column(name = "ruc", nullable = false)
    private String ruc;

    @Column(name = "acreditado_por")
    private String acreditadoPor;

    @Column(name = "numero_acreditacion")
    private String numeroAcreditacion;

    @Column(name = "ruta_doc_acreditacion")
    private String rutaDocAcreditacion;

    @Column(name = "nombre_doc_acreditacion")
    private String nombreDocAcreditacion;

    @Lob
    @Column(name = "documento_acreditacion")
    private byte[] documentoAcreditacion;

    @Column(name = "documento_acreditacion_content_type")
    private String documentoAcreditacionContentType;

    @Column(name = "vigencia_desde")
    private LocalDate vigenciaDesde;

    @Column(name = "vigencia_hasta")
    private LocalDate vigenciaHasta;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;

    @Column(name = "nombre_contacto")
    private String nombreContacto;

    @Column(name = "fecha_creacion")
    private LocalDate fechaCreacion;

    @OneToMany(mappedBy = "laboratorio")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Monitorista> laboratorios = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public Laboratorio razonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
        return this;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public Laboratorio direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public Laboratorio ruc(String ruc) {
        this.ruc = ruc;
        return this;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getAcreditadoPor() {
        return acreditadoPor;
    }

    public Laboratorio acreditadoPor(String acreditadoPor) {
        this.acreditadoPor = acreditadoPor;
        return this;
    }

    public void setAcreditadoPor(String acreditadoPor) {
        this.acreditadoPor = acreditadoPor;
    }

    public String getNumeroAcreditacion() {
        return numeroAcreditacion;
    }

    public Laboratorio numeroAcreditacion(String numeroAcreditacion) {
        this.numeroAcreditacion = numeroAcreditacion;
        return this;
    }

    public void setNumeroAcreditacion(String numeroAcreditacion) {
        this.numeroAcreditacion = numeroAcreditacion;
    }

    public String getRutaDocAcreditacion() {
        return rutaDocAcreditacion;
    }

    public Laboratorio rutaDocAcreditacion(String rutaDocAcreditacion) {
        this.rutaDocAcreditacion = rutaDocAcreditacion;
        return this;
    }

    public void setRutaDocAcreditacion(String rutaDocAcreditacion) {
        this.rutaDocAcreditacion = rutaDocAcreditacion;
    }

    public String getNombreDocAcreditacion() {
        return nombreDocAcreditacion;
    }

    public Laboratorio nombreDocAcreditacion(String nombreDocAcreditacion) {
        this.nombreDocAcreditacion = nombreDocAcreditacion;
        return this;
    }

    public void setNombreDocAcreditacion(String nombreDocAcreditacion) {
        this.nombreDocAcreditacion = nombreDocAcreditacion;
    }

    public byte[] getDocumentoAcreditacion() {
        return documentoAcreditacion;
    }

    public Laboratorio documentoAcreditacion(byte[] documentoAcreditacion) {
        this.documentoAcreditacion = documentoAcreditacion;
        return this;
    }

    public void setDocumentoAcreditacion(byte[] documentoAcreditacion) {
        this.documentoAcreditacion = documentoAcreditacion;
    }

    public String getDocumentoAcreditacionContentType() {
        return documentoAcreditacionContentType;
    }

    public Laboratorio documentoAcreditacionContentType(String documentoAcreditacionContentType) {
        this.documentoAcreditacionContentType = documentoAcreditacionContentType;
        return this;
    }

    public void setDocumentoAcreditacionContentType(String documentoAcreditacionContentType) {
        this.documentoAcreditacionContentType = documentoAcreditacionContentType;
    }

    public LocalDate getVigenciaDesde() {
        return vigenciaDesde;
    }

    public Laboratorio vigenciaDesde(LocalDate vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
        return this;
    }

    public void setVigenciaDesde(LocalDate vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public LocalDate getVigenciaHasta() {
        return vigenciaHasta;
    }

    public Laboratorio vigenciaHasta(LocalDate vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
        return this;
    }

    public void setVigenciaHasta(LocalDate vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }

    public String getTelefono() {
        return telefono;
    }

    public Laboratorio telefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public Laboratorio correo(String correo) {
        this.correo = correo;
        return this;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public Laboratorio nombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
        return this;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public Laboratorio fechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Set<Monitorista> getLaboratorios() {
        return laboratorios;
    }

    public Laboratorio laboratorios(Set<Monitorista> monitoristas) {
        this.laboratorios = monitoristas;
        return this;
    }

    public Laboratorio addLaboratorio(Monitorista monitorista) {
        this.laboratorios.add(monitorista);
        monitorista.setLaboratorio(this);
        return this;
    }

    public Laboratorio removeLaboratorio(Monitorista monitorista) {
        this.laboratorios.remove(monitorista);
        monitorista.setLaboratorio(null);
        return this;
    }

    public void setLaboratorios(Set<Monitorista> monitoristas) {
        this.laboratorios = monitoristas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Laboratorio)) {
            return false;
        }
        return id != null && id.equals(((Laboratorio) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Laboratorio{" +
            "id=" + getId() +
            ", razonSocial='" + getRazonSocial() + "'" +
            ", direccion='" + getDireccion() + "'" +
            ", ruc='" + getRuc() + "'" +
            ", acreditadoPor='" + getAcreditadoPor() + "'" +
            ", numeroAcreditacion='" + getNumeroAcreditacion() + "'" +
            ", rutaDocAcreditacion='" + getRutaDocAcreditacion() + "'" +
            ", nombreDocAcreditacion='" + getNombreDocAcreditacion() + "'" +
            ", documentoAcreditacion='" + getDocumentoAcreditacion() + "'" +
            ", documentoAcreditacionContentType='" + getDocumentoAcreditacionContentType() + "'" +
            ", vigenciaDesde='" + getVigenciaDesde() + "'" +
            ", vigenciaHasta='" + getVigenciaHasta() + "'" +
            ", telefono='" + getTelefono() + "'" +
            ", correo='" + getCorreo() + "'" +
            ", nombreContacto='" + getNombreContacto() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            "}";
    }
}
