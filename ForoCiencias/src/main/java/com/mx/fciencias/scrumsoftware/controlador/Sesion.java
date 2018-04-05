package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Credencial;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.InicioSesionIH;
import java.util.Locale;
import javax.faces.application.FacesMessage;
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
    private ConexionBD controladorJPA;
    /* Credencial de la sesion de usuario */
    private InicioSesionIH credencial;
    /* Contador de intentos para iniciar sesion */
    private int intentos;

	// Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public Sesion() {
    	FacesContext.getCurrentInstance().getViewRoot().setLocale( new Locale( "es-Mx" ) );
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
        credencial = new InicioSesionIH();
        intentos = 0;
    }

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la credencial de esta sesion de usuario.
     * @return <code>Credencial</code> - La credencial de la sesion de usuario.
     */
    public InicioSesionIH getCredencial() {
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
    public void setCredencial( InicioSesionIH nuevaCredencial ) {
        this.credencial = nuevaCredencial;
    }

	// Metodos de implementacion.
	/**
	 * Inicializa la sesion de usuario a partir de la credencial identificada.
	 * @return <code>String</code> - La direccion de la interfaz de usuario.
	 */
    public String iniciarSesion() {
        Credencial l = controladorJPA.consultarRegistro( credencial.getNombreUsuario(), credencial.getContrasena() );
        boolean logged = l != null;
        if ( logged ) {
        	FacesContext context = getCurrentInstance();
        	context.getExternalContext().getSessionMap().put( "usuario", l );
        	intentos = 0;
        	return "PrincipalUsuarioIH?faces-redirect=true";
        }
        else {
        	intentos++;
        	if ( intentos == 3 ) {
        		return "ErrorInicioSesionIH?faces-redirect=true";
        	}
        	else {
        		FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "Nombre de usuario y/o contraseña inválidos, o la cuenta no ha sido verificada.\n Inténtelo nuevamente.", "" ) );
        		return null;
        	}
        }
    }
    
    
	/**
	 * FInaliza la sesion actual.
	 * @return <code>String</code> - La direccion de la interfaz de visitante.
	 */    
    public String cerrarSesion() {
        try {
        	Credencial l = controladorJPA.consultarRegistro( credencial.getNombreUsuario(), credencial.getContrasena() );
        	FacesContext context = getCurrentInstance();
        	context.getExternalContext().invalidateSession();
        	return "FinSesionIH?faces-redirect=true";
        }
        catch ( PersistenceException e ) {
        	return "ErrorFinSesionIH?faces-redirect=true";
        }
	}
}
