package com.mx.fciencias.scrumsoftware.web;

/**
 *  La clase <code>Credencial</code> define objetos que permiten manejar la informacion
 * de un usuario para iniciar sesion en el sistema.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class Credencial {

	// Atributos.
	/* Nombre de usuario */
    public String nombreUsuario;
    
	/* Contraseña de usuario */    
    public String contrasena;

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
