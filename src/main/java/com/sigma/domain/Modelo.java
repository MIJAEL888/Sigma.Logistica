package com.sigma.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Modelo.
 */
@Entity
@Table(name = "modelo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Modelo implements Serializable {

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

    @OneToMany(mappedBy = "modelo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Equipo> equipos = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("modelos")
    private TipoEquipo tipoEuipo;

    @ManyToOne
    @JsonIgnoreProperties("modelos")
    private Marca marca;

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

    public Modelo nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Modelo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Equipo> getEquipos() {
        return equipos;
    }

    public Modelo equipos(Set<Equipo> equipos) {
        this.equipos = equipos;
        return this;
    }

    public Modelo addEquipo(Equipo equipo) {
        this.equipos.add(equipo);
        equipo.setModelo(this);
        return this;
    }

    public Modelo removeEquipo(Equipo equipo) {
        this.equipos.remove(equipo);
        equipo.setModelo(null);
        return this;
    }

    public void setEquipos(Set<Equipo> equipos) {
        this.equipos = equipos;
    }

    public TipoEquipo getTipoEuipo() {
        return tipoEuipo;
    }

    public Modelo tipoEuipo(TipoEquipo tipoEquipo) {
        this.tipoEuipo = tipoEquipo;
        return this;
    }

    public void setTipoEuipo(TipoEquipo tipoEquipo) {
        this.tipoEuipo = tipoEquipo;
    }

    public Marca getMarca() {
        return marca;
    }

    public Modelo marca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Modelo)) {
            return false;
        }
        return id != null && id.equals(((Modelo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Modelo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
