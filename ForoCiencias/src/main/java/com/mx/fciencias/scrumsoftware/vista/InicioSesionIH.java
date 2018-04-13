package com.mx.fciencias.scrumsoftware.vista;

/**
 *  La clase <code>InicioSesionIH</code> define objetos que permiten manejar la informacion
 * de un usuario para iniciar sesion en el sistema.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class InicioSesionIH {

    // Atributos.
    /* Nombre de usuario */
    private String nombreUsuario;
    /* Contraseña de usuario */    
    private String contrasena;

    // Metodos de acceso y modificacion.
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
}