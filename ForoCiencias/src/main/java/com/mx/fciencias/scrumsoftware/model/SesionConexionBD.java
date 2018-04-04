package com.mx.fciencias.scrumsoftware.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *  Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table( catalog = "forociencias", schema = "modeloforo", uniqueConstraints = { @UniqueConstraint( columnNames = { "nombreusuario" } ) } )
@XmlRootElement
@NamedQueries( { @NamedQuery(name = "SesionConexionBD.findAll", query = "SELECT l FROM SesionConexionBD l" ),
                                 //@NamedQuery(name = "SesionConexionBD.insertUsuario", 
                                   //      query = "INSERT INTO SesionConexionBD( nombreUsuario, correoCiencias, contrasena, genero, fechaNacimiento ) "
                                     //            + "VALUES ( l.nombreUsuario, 'luis_lazaro@ciencias.unam.mx', l.contraseña, 'Masculino', '1988-02-25' );" ),
                                 
				 @NamedQuery(name = "SesionConexionBD.findById", query = "SELECT l FROM SesionConexionBD l WHERE l.idUsuario = :id" ),
				 @NamedQuery(name = "SesionConexionBD.findByUsuario", query = "SELECT l FROM Usuario l WHERE l.nombreUsuario = :nombreUsuario" ),
				 @NamedQuery(name = "SesionConexionBD.findByPassword", query = "SELECT l FROM SesionConexionBD l WHERE l.contrasena = :contrasena" ) } )
@NamedNativeQueries(value = { @NamedNativeQuery( name = "SesionConexionBD.canSesionConexionBD", query = "select modeloforo.verificar(?, ?)" ),
							  @NamedNativeQuery( name = "SesionConexionBD.findByUsuarioAndPassword",
												 query = "SELECT idUsuario, nombreUsuario FROM modeloforo.usuario WHERE nombreusuario = ?1 AND contrasena = crypt(?2, contrasena)",
												 resultClass = SesionConexionBD.class ) } )

/**
 *  La clase <code>SesionConexionBD</code> define objetos que permiten consultar la base de datos del
 * sistema con la finalidad de validar y recuperar la informacion de los usuaros registrados.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
 */
public class SesionConexionBD implements Serializable {

	// Atributos.
	/* Llave primaria del usuario dentro de la BD */
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idUsuario;
    /* Nombre del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String nombreUsuario;
    /* Contraseña del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String contrasena;

	// Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public SesionConexionBD() {
    }

    /**
     * Permite crear un objeto de tipo <code>SesionConexionBD</code> a partir de una Llave primaria.
     * @param idUsuario - La llave primaria.
     */
    public SesionConexionBD( Integer idUsuario ) {
        this.idUsuario = idUsuario;
    }

    /**
     * Permite crear un objeto de tipo <code>SesionConexionBD</code> a partir de una Llave primaria, un
     * nombre de usuario y una contraseña.
     * @param idUsuario - La llave primaria.
     * @param nombreUsuario - El nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     */
    public SesionConexionBD( Integer idUsuario, String nombreUusuario, String contrasena ) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la llave primaria de este objeto.
     * @return <code>Integer</code> - La llave primaria.
     */
    public Integer getIdUsuario() {
        return idUsuario;
    }

    /**
     * Cambia la llave primaria de este objeto por el que se pasa como parametro.
     * @param nuevoElemento - El nuevo elemento contenido es este
     * <code>Nodo</code>.
     */
    public void setIdUsuario( Integer nuevoIdUsuario ) {
        this.idUsuario = nuevoIdUsuario;
    }

    /**
     * Devueleve el nombre de usuario de este objeto.
     * @return <code>String</code> - El nombre de usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Cambia el nombre de usuario de este objeto por el que se pasa como parametro.
     * @param nuevoNombreUsuario - El nuevo nombre de usuario.
     */
    public void setNombreUsuario( String nuevoNombreUsuario ) {
        this.nombreUsuario = nuevoNombreUsuario;
    }

    /**
     * Devueleve la contraseña de usuario de este objeto.
     * @return <code>String</code> - La contraseña de usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * Cambia la contraseña de usuario de este objeto por la que se pasa como parametro.
     * @param nuevaContrasena - La nueva contrasena de usuario.
     */
    public void setContrasena( String nuevaContrasena ) {
        this.contrasena = nuevaContrasena;
    }

    // Metodos de implementacion.
    /**
     * Devueleve el codigo hash correspondiente a la llave primaria de este objeto.
     * @return <code>int</code> - El codigo hash.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += ( idUsuario != null ? idUsuario.hashCode() : 0 );
        return hash;
    }

    /**
     * Determina si el objeto pasado como parametro es igual a este objeto.
     * @param object - El objeto a comparar.
     * @return <code>boolean</code> - true si soniguales, false en otro caso.
     */
    @Override
    public boolean equals( Object object ) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if ( !( object instanceof SesionConexionBD ) ) {
            return false;
        }
        SesionConexionBD other = ( SesionConexionBD ) object;
        if ( ( this.idUsuario == null && other.idUsuario != null ) || ( this.idUsuario != null && !this.idUsuario.equals( other.idUsuario ) ) ) {
            return false;
        }
        return true;
    }

    /**
     * Convierte este objeto a cadena.
     * @return <code>String</code> - La representacion en cadena.
     */
    @Override
    public String toString() {
        return "com.mx.fciencias.scrumsoftware.model.model.SesionConexionBD[ idUsuario=" + idUsuario + " ]";
    }

}
