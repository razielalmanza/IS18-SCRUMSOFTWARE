package com.mx.fciencias.scrumsoftware.modelo;

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
@Table( catalog = "forociencias", schema = "modeloforo" )
@XmlRootElement
@NamedQueries( { @NamedQuery(name = "Credencial.findAll", query = "SELECT l FROM Credencial l" ),
		 @NamedQuery(name = "Credencial.findById", query = "SELECT l FROM Credencial l WHERE l.idUsuario = :id" ),
		 @NamedQuery(name = "Credencial.findByUsuario", query = "SELECT l FROM Credencial l WHERE l.nombreUsuario = :nombreUsuario" ),
		 @NamedQuery(name = "Credencial.findByPassword", query = "SELECT l FROM Credencial l WHERE l.contrasena = :contrasena" ) } )
@NamedNativeQueries( value = { @NamedNativeQuery( name = "Credencial.canLogin", query = "SELECT modeloforo.verificar(?, ?)" ),
                               @NamedNativeQuery( name = "Credencial.findByUsuarioAndPassword",
                                                  query = "SELECT idUsuario, nombreUsuario, administrador FROM modeloforo.usuario WHERE nombreusuario = ?1 AND contrasena = crypt(?2, contrasena)",
						  resultClass = Credencial.class ) } )

/**
 *  La clase <code>Credencial</code> define objetos que permiten consultar la base de datos del
 * sistema con la finalidad de validar y recuperar la informacion de los usuaros registrados.
 *
 * Creado y/o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
 */
public class Credencial implements Serializable {

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
    /* Rol del usuario */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private char administrador;

    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Credencial() {
    }

    /**
     * Permite crear un objeto de tipo <code>Credencial</code> a partir de una Llave primaria.
     * @param idUsuario - La llave primaria.
     */
    public Credencial( Integer idUsuario ) {
        this.idUsuario = idUsuario;
    }

    /**
     * Permite crear un objeto de tipo <code>Credencial</code> a partir de una Llave primaria, un
     * nombre de usuario y una contraseña.
     * @param idUsuario - La llave primaria.
     * @param nombreUsuario - El nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     */
    public Credencial( Integer idUsuario, String nombreUsuario, String contrasena ) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.administrador = 'N';
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
    
    /**
     * Devueleve el rol de este usuario.
     * @return <code>char</code> - 'S' si el usuario es administrador, 'N' en otro caso.
     */
    public char getAdministrador() {
        return administrador;
    }

    /**
     * Cambia el rol del usuario.
     * @param nuevoRol - El nuevo rol del usuario.
     */
    public void setAdministrador( char nuevoRol ) {
        this.administrador = nuevoRol;
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
        if ( !( object instanceof Credencial ) ) {
            return false;
        }
        Credencial other = ( Credencial ) object;
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
        return "com.mx.fciencias.scrumsoftware.model.model.Credencial[ idUsuario=" + idUsuario + " ]";
    }
}
