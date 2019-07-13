package com.sigma.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A TipoSeguro.
 */
@Entity
@Table(name = "tipo_seguro")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoSeguro implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Lob
    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "fecha_caudicidad")
    private LocalDate fechaCaudicidad;

    @Column(name = "codigo_tipo_seguro")
    private Integer codigoTipoSeguro;

    @ManyToMany(mappedBy = "tipoSeguros")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Monitorista> monitoristas = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public TipoSeguro nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public TipoSeguro descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaCaudicidad() {
        return fechaCaudicidad;
    }

    public TipoSeguro fechaCaudicidad(LocalDate fechaCaudicidad) {
        this.fechaCaudicidad = fechaCaudicidad;
        return this;
    }

    public void setFechaCaudicidad(LocalDate fechaCaudicidad) {
        this.fechaCaudicidad = fechaCaudicidad;
    }

    public Integer getCodigoTipoSeguro() {
        return codigoTipoSeguro;
    }

    public TipoSeguro codigoTipoSeguro(Integer codigoTipoSeguro) {
        this.codigoTipoSeguro = codigoTipoSeguro;
        return this;
    }

    public void setCodigoTipoSeguro(Integer codigoTipoSeguro) {
        this.codigoTipoSeguro = codigoTipoSeguro;
    }

    public Set<Monitorista> getMonitoristas() {
        return monitoristas;
    }

    public TipoSeguro monitoristas(Set<Monitorista> monitoristas) {
        this.monitoristas = monitoristas;
        return this;
    }

    public TipoSeguro addMonitorista(Monitorista monitorista) {
        this.monitoristas.add(monitorista);
        monitorista.getTipoSeguros().add(this);
        return this;
    }

    public TipoSeguro removeMonitorista(Monitorista monitorista) {
        this.monitoristas.remove(monitorista);
        monitorista.getTipoSeguros().remove(this);
        return this;
    }

    public void setMonitoristas(Set<Monitorista> monitoristas) {
        this.monitoristas = monitoristas;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoSeguro)) {
            return false;
        }
        return id != null && id.equals(((TipoSeguro) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "TipoSeguro{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", fechaCaudicidad='" + getFechaCaudicidad() + "'" +
            ", codigoTipoSeguro=" + getCodigoTipoSeguro() +
            "}";
    }
}
