package com.mx.fciencias.scrumsoftware.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;

/**
 *  Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table( catalog = "forociencias", schema = "modeloforo" )
@XmlRootElement
@NamedQueries( { @NamedQuery( name = "Pregunta.findAll", query = "SELECT p FROM Pregunta p" ) } )
@NamedNativeQueries( value = { @NamedNativeQuery( name = "Pregunta.eliminar", query = "SELECT modeloforo.eliminarpregunta(?)" ) } )

/**
 *  La clase <code>Pregunta</code>.
 *
 * Creado y/o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro">Jose Luis Vazquez Lazaro</a>
 * @version 1.3
 */
public class Pregunta implements Serializable {

    // Atributos.
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idPregunta;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String titulo;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String contenido;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private Timestamp fechaPregunta;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private Integer idUsuario;
   
    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Pregunta() {
    }

    public Pregunta( String titulo, String contenido, Integer idUsuario ) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPregunta = new Timestamp( System.currentTimeMillis() );
        this.idUsuario = idUsuario;
    }

    // Metodos de acceso y modificacion.
    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta( Integer nuevoIdPregunta ) {
        this.idPregunta = nuevoIdPregunta;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo( String nuevoTitulo ) {
        this.titulo = nuevoTitulo;
    }
    
    public String getContenido() {
        return contenido;
    }

    public void setContenido( String nuevoContenido ) {
        this.contenido = nuevoContenido;
    }

    public Timestamp getFechaPregunta() {
        return fechaPregunta;
    }

    public void setFechaPregunta( Timestamp nuevaFechaPregunta ) {
        this.fechaPregunta = nuevaFechaPregunta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario( Integer nuevoIdUsuario ) {
        this.idUsuario = nuevoIdUsuario;
    }
}
