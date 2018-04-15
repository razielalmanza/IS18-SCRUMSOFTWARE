package com.mx.fciencias.scrumsoftware.vista;

import java.util.Date;

/**
 *  La clase <code>ResgistroIH</code>.
 *
 * Creado y/o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:"></a>
 * @version 1.1
 */
public class RegistroIH {
    
    private String usuario;
    private String contraseña;
    private String confirmacionContraseña;
    private String correo;
    private Date fechaNac;
    private String genero;
    
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getConfirmacionContraseña() {
        return confirmacionContraseña;
    }

    public void setConfirmacionContraseña(String confirmacionContraseña) {
        this.confirmacionContraseña = confirmacionContraseña;
    }

}
