package com.mx.fciencias.scrumsoftware.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.sql.Timestamp;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;

/**
 *  Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table( catalog = "forociencias", schema = "modeloforo" )
@XmlRootElement
@NamedQueries( { @NamedQuery( name = "Respuesta.findAll", query = "SELECT p FROM Respuesta p WHERE p.idPregunta = :idPregunta" ) } )
@NamedNativeQueries( value = { @NamedNativeQuery( name = "Respuesta.eliminar", query = "SELECT modeloforo.eliminarRespuesta(?)" ) } )

/**
 *  La clase <code>Respuesta</code>.
 *
 * Creado y/o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:"></a>
 * @version 1.3
 */
public class Respuesta implements Serializable {

    // Atributos.
    /* Llave primaria del usuario dentro de la BD */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idRespuesta;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String contenido;

    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private Timestamp fechaRespuesta;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idPregunta;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idUsuario;

    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Respuesta() {
    }

    public Respuesta( String contenido, Integer idPregunta, Integer idUsuario ) {
        this.contenido = contenido;
        this.fechaRespuesta = new Timestamp( System.currentTimeMillis() );
        this.idPregunta = idPregunta;
        this.idUsuario = idUsuario;
    }

    // Metodos de acceso y modificacion.
    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta( Integer nuevoIdRespuesta ) {
        this.idRespuesta = nuevoIdRespuesta;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido( String nuevoContenido ) {
        this.contenido = nuevoContenido;
    }

    public Timestamp getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta( Timestamp nuevaFechaRespuesta ) {
        this.fechaRespuesta = nuevaFechaRespuesta;
    }
   
    public Integer getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta( Integer nuevoIdPregunta ) {
        this.idPregunta = nuevoIdPregunta;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdusuario( Integer nuevoIdUsuario ) {
        this.idUsuario = nuevoIdUsuario;
    }
}
