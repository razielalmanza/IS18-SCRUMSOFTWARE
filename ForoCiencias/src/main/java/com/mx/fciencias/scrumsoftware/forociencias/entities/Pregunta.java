package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(catalog= "forociencias", schema= "preguntas", name="pregunta")
@NamedQueries({
    @NamedQuery(name = "Pregunta.findAll", query= "SELECT p FROM Pregunta p")
})
public class Pregunta implements Serializable {
    
    @EmbeddedId
    private PreguntaPK id;
    
    @Column(name="titulo")
    private String titulo;
    
    @Column(name="contenido")
    private String contenido;
    
    @Column(name="fecha_y_hora")
    private Timestamp fechaYHora;
    
    public PreguntaPK getPreguntaPK() {
        return id;
    }
    
    public void setPreguntaPK(PreguntaPK id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(Timestamp fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
    
}
