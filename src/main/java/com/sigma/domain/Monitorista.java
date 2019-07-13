package com.sigma.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Monitorista.
 */
@Entity
@Table(name = "monitorista")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Monitorista implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @NotNull
    @Column(name = "apellido_paterno", nullable = false)
    private String apellidoPaterno;

    @NotNull
    @Column(name = "apellido_materno", nullable = false)
    private String apellidoMaterno;

    @NotNull
    @Column(name = "dni", nullable = false)
    private String dni;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JsonIgnoreProperties("monitoristas")
    private Laboratorio laboratorio;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "monitorista_tipo_seguro",
               joinColumns = @JoinColumn(name = "monitorista_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tipo_seguro_id", referencedColumnName = "id"))
    private Set<TipoSeguro> tipoSeguros = new HashSet<>();

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

    public Monitorista nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public Monitorista apellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        return this;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public Monitorista apellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        return this;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getDni() {
        return dni;
    }

    public Monitorista dni(String dni) {
        this.dni = dni;
        return this;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Monitorista fechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
        return this;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public Monitorista laboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
        return this;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Set<TipoSeguro> getTipoSeguros() {
        return tipoSeguros;
    }

    public Monitorista tipoSeguros(Set<TipoSeguro> tipoSeguros) {
        this.tipoSeguros = tipoSeguros;
        return this;
    }

    public Monitorista addTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguros.add(tipoSeguro);
        tipoSeguro.getMonitoristas().add(this);
        return this;
    }

    public Monitorista removeTipoSeguro(TipoSeguro tipoSeguro) {
        this.tipoSeguros.remove(tipoSeguro);
        tipoSeguro.getMonitoristas().remove(this);
        return this;
    }

    public void setTipoSeguros(Set<TipoSeguro> tipoSeguros) {
        this.tipoSeguros = tipoSeguros;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Monitorista)) {
            return false;
        }
        return id != null && id.equals(((Monitorista) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Monitorista{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellidoPaterno='" + getApellidoPaterno() + "'" +
            ", apellidoMaterno='" + getApellidoMaterno() + "'" +
            ", dni='" + getDni() + "'" +
            ", fechaNacimiento='" + getFechaNacimiento() + "'" +
            "}";
    }
}
