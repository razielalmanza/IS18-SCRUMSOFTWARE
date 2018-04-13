package com.mx.fciencias.scrumsoftware.vista;

import com.mx.fciencias.scrumsoftware.modelo.Credencial;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *  La clase <code>InicioSesionIHBean</code> define objetos que permiten manejar la sesion de un
 * usuario a partir de la interfaz de usuario.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class InicioSesionIHBean {

    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public InicioSesionIHBean() {
    }

    // Metodos de implementacion.
    /**
     * Determina si el usuario actual esta registrado en la base de datos del sistema.
     * @return <code>boolean</code> - true si el usuario esta registrado, false en otro caso.
     */
    public boolean estaRegistrado() {
        FacesContext context = getCurrentInstance();
        Credencial l = ( Credencial ) context.getExternalContext().getSessionMap().get( "usuario" );
        return l != null;
    }

    /**
     * Devuelve la informacion basica del usuario actual: id de usuario, nombre de usuario y contraseña.
     * @return <code>Credencial</code> - La informacon basica del usuario.
     */
    public Credencial getUsuario() {
        FacesContext context = getCurrentInstance();
        return ( Credencial ) context.getExternalContext().getSessionMap().get( "usuario" );
    }

}