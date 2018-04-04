package com.mx.fciencias.scrumsoftware.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Clase que representa la entidad preguntas.pregunta.
 * @author Marco Olea
 */
@Entity
@Table(catalog= "forociencias", schema= "preguntas", name="pregunta")
@NamedQueries({
    /* Selecciona todas las preguntas. */
    @NamedQuery(name = "Pregunta.findAll", query= "SELECT p FROM Pregunta p")
})
public class Pregunta implements Serializable {
    
    /*
     * La llave primaria compuesta, que consiste del identificador del usuario y
     * el identificador de la pregunta.
     */
    @EmbeddedId
    private PreguntaPK id;
    
    /* Título de la pregunta. */
    @Column(name="titulo")
    private String titulo;
    
    /* Contenido de la pregunta. */
    @Column(name="contenido")
    private String contenido;
    
    /* 
     * Un java.sql.Timestamp que representa la fecha y hora en que se realizó la
     * pregunta.
     */
    @Column(name="fecha_y_hora")
    private Timestamp fechaYHora;
    
    /**
     * Regresa la llave primaria de esta pregunta.
     * @return una instancia de PreguntaPK
     */
    public PreguntaPK getPreguntaPK() {
        return id;
    }
    
    /**
     * Fija una nueva llave primaria para esta pregunta.
     * @param id la nueva llave primaria.
     */
    public void setPreguntaPK(PreguntaPK id) {
        this.id = id;
    }

    /**
     * Regresa el título de esta pregunta.
     * @return un String que representa el título.
     */
    public String getTitulo() {
        return titulo;
    }
    
    /**
     * Fija un nuevo título para esta pregunta.
     * @param titulo el nuevo título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Regresa el contenido de esta pregunta.
     * @return un String que representa el contenido.
     */
    public String getContenido() {
        return contenido;
    }
    
    /**
     * Fija el nuevo contenido de esta pregunta.
     * @param contenido el nuevo contenido.
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    /**
     * Regresa la fecha y hora en que se realizó esta pregunta.
     * @return un java.sql.Timestamp que representa la fecha y hora.
     */
    public Timestamp getFechaYHora() {
        return fechaYHora;
    }

    /**
     * Fija una nueva fecha y hora de realización de esta pregunta.
     * @param fechaYHora la nueva fecha y hora.
     */
    public void setFechaYHora(Timestamp fechaYHora) {
        this.fechaYHora = fechaYHora;
    }
    
}
