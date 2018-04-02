package com.mx.fciencias.scrumsoftware.web;

import com.mx.fciencias.scrumsoftware.model.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.model.SesionConexionBD;
import com.mx.fciencias.scrumsoftware.model.SesionJPA;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

import static javax.faces.context.FacesContext.getCurrentInstance;

/**
 *  La clase <code>Sesion</code> define objetos que permiten manejar la sesion de un
 * usuario registrado en el sistema a partir de su credencial de usuario.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.5
 */
@ManagedBean
@SessionScoped
public class Sesion {

	// Atributos.
	/* Entidad para la persistencia de una sesion de usuario */
    private EntityManagerFactory entidad;
    /* Sesion de usuario */
    private SesionJPA controladorJPA;
    /* Credencial de la sesion de usuario */
    private Credencial credencial;
    /* Contador de intentos para iniciar sesion */
    private int intentos;

	// Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Sesion() {
    	FacesContext.getCurrentInstance().getViewRoot().setLocale( new Locale( "es-Mx" ) );
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new SesionJPA( entidad );
        credencial = new Credencial();
        intentos = 0;
    }

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la credencial de esta sesion de usuario.
     * @return <code>Credencial</code> - La credencial de la sesion de usuario.
     */
    public Credencial getCredencial() {
        return credencial;
    }
    
    /**
     * Devueleve el numero de intentos de iniciar sesion.
     * @return <code>int</code> - El numero de intentos.
     */
    public int getIntentos() {
        return intentos;
    }

    /**
     * Cambia la credencial de la sesion de usuario por la que se pasa como parametro.
     * @param nuevaCredencial - La nueva credencial de usuario.
     */
    public void setCredencial( Credencial nuevaCredencial ) {
        this.credencial = nuevaCredencial;
    }

	// Metodos de implementacion.
	/**
	 * Inicializa la sesion de usuario a partir de la credencial identificada.
	 * @return <code>String</code> - La direccion de la interfaz de usuario.
	 */
    public String iniciarSesion() {
        SesionConexionBD l = controladorJPA.consultarRegistro( credencial.getNombreUsuario(), credencial.getContrasena() );
        boolean logged = l != null;
        if ( logged ) {
        	FacesContext context = getCurrentInstance();
        	context.getExternalContext().getSessionMap().put( "usuario", l );
        	intentos = 0;
        	return "PrincipalUsuarioIH?faces-redirect=true";
        }
        else {
        	intentos++;
        	return "ErrorInicioSesionIH?faces-redirect=true";
        }
    }
    
    
	/**
	 * FInaliza la sesion actual.
	 * @return <code>String</code> - La direccion de la interfaz de visitante.
	 */    
    public String cerrarSesion() {
        try {
        	SesionConexionBD l = controladorJPA.consultarRegistro( credencial.getNombreUsuario(), credencial.getContrasena() );
        	FacesContext context = getCurrentInstance();
        	context.getExternalContext().invalidateSession();
        	return "FinSesionIH?faces-redirect=true";
        }
        catch ( PersistenceException e ) {
        	return "ErrorFinSesionIH?faces-redirect=true";
        }
	}
}
