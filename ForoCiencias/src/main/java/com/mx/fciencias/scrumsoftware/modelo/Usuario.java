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
import java.sql.Date;

/**
 *  Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table( catalog = "forociencias", schema = "modeloforo", uniqueConstraints = { @UniqueConstraint( columnNames = { "nombreusuario" } ) } )
@XmlRootElement
@NamedQueries( { @NamedQuery(name = "Usuario.findById", query = "SELECT l FROM Usuario l WHERE l.idUsuario = :idUsuario" ) ,
                 @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT l FROM Usuario l WHERE l.nombreUsuario = :nombreUsuario" ) } )

/**
 *  La clase <code>Usuario</code>. 
 *
 * Creado o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:"></a>
 * @version 1.3
 */
public class Usuario implements Serializable {

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
    /* correoCiencias del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String correoCiencias;
    /* Contraseña del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String contrasena;
    /* Genero del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private String genero;
    /* fechaNacimiento de nac del usuario dentro de la BD */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private Date fechaNacimiento;
    /* cuentaVerificada del usuario */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private char cuentaVerificada;
    /* indica el rol del usuario: normal o administrador */
    @Basic(optional = false)
    @Column(nullable = false, length = 2147483647)
    private char administrador;
    
    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Usuario() {
    }

    /**
     * Permite crear un objeto de tipo <code>Usuario</code> a partir de una Llave primaria, un
     * nombre de usuario y una contraseña.
     * @param idUsuario - La llave primaria.
     * @param nombreUsuario - El nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     */
    public Usuario( String nombreUsuario, String correoCiencias, String contrasena, String genero, Date fechaNacimiento ) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.fechaNacimiento = fechaNacimiento;
        this.correoCiencias = correoCiencias;
        this.genero = genero;
        this.cuentaVerificada = 'N';
        this.administrador = 'N';
    }

    public char getAdministrador() {
        return administrador;
    }

    public void setAdministrador( char administrador ) {
        this.administrador = administrador;
    }
    
    public char getcuentaVerificada() {
        return cuentaVerificada;
    }

    public void setcuentaVerificada(char cuentaVerificada) {
        this.cuentaVerificada = cuentaVerificada;
    }

    public String getcorreoCiencias() {
        return correoCiencias;
    }

    public void setcorreoCiencias(String correoCiencias) {
        this.correoCiencias = correoCiencias;
    }

    public Date getfechaNacimiento() {
        return fechaNacimiento;
    }

    public void setfechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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
        if ( !( object instanceof Usuario ) ) {
            return false;
        }
        Usuario other = ( Usuario ) object;
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
        return "com.mx.fciencias.scrumsoftware.model.model.Usuario[ idUsuario=" + idUsuario + " ]";
    }
}
