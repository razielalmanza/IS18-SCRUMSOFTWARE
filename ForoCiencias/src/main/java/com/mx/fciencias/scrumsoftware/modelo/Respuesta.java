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
/**
 *  Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table( catalog = "forociencias", schema = "modeloforo", uniqueConstraints = { @UniqueConstraint( columnNames = { "nombreusuario" } ) } )
@XmlRootElement
@NamedQueries( { @NamedQuery(name = "RespuestaConexionBD.findAll", query = "SELECT l FROM RespuestaConexionBD l" ),
                                 
				 @NamedQuery(name = "RespuestaConexionBD.findById", query = "SELECT l FROM RespuestaConexionBD l WHERE l.idPregunta = :id" ),
				 @NamedQuery(name = "RespuestaConexionBD.findByAnswer", query = "SELECT l FROM RespuestaConexionBD l WHERE l.respuesta = :respuesta" ) } )

/**
 *  La clase <code>SesionConexionBD</code> define objetos que permiten consultar la base de datos del
 * sistema con la finalidad de validar y recuperar la informacion de los usuaros registrados.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
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
    /* Nombre del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String contenido;
     
    
   
    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Respuesta() {
    }


    /**
     * Permite crear un objeto de tipo <code>Usuario</code> a partir de una Llave primaria, un
     * nombre de usuario y una contraseña.
     * @param idUsuario - La llave primaria.
     * @param nombreUsuario - El nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     */
    public Respuesta(String contenido ) {
        this.contenido = contenido;
    }

    
   

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la llave primaria de este objeto.
     * @return <code>Integer</code> - La llave primaria.
     */
    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    /**
     * Cambia la llave primaria de este objeto por el que se pasa como parametro.
     * @param nuevoElemento - El nuevo elemento contenido es este
     * <code>Nodo</code>.
     */
    public void setIdUsuario( Integer nuevoIsRespuesta ) {
        this.idRespuesta = nuevoIsRespuesta;
    }

    /**
     * Devueleve el nombre de usuario de este objeto.
     * @return <code>String</code> - El nombre de usuario.
     */
    public String getContenido() {
        return contenido;
    }

    /**
     * Cambia el nombre de usuario de este objeto por el que se pasa como parametro.
     * @param nuevoNombreUsuario - El nuevo nombre de usuario.
     */
    public void setContenido( String nuevoContenido ) {
        this.contenido = nuevoContenido;
    }

    
   
    /**
     * Convierte este objeto a cadena.
     * @return <code>String</code> - La representacion en cadena.
     */
    @Override
    public String toString() {
        return "com.mx.fciencias.scrumsoftware.model.model.Usuario[ idRespuesta=" + idRespuesta + " ]";
    }

}
