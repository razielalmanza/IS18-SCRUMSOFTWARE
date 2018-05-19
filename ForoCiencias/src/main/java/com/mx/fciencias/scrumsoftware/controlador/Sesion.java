package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Credencial;
import com.mx.fciencias.scrumsoftware.modelo.Pregunta;
import com.mx.fciencias.scrumsoftware.modelo.Respuesta;
import com.mx.fciencias.scrumsoftware.modelo.Usuario;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.InicioSesionIH;
import java.util.*;
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
 * @version 1.7
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
        boolean logged = controladorJPA.estaRegistrado( credencial.getNombreUsuario(), credencial.getContrasena() );
        Credencial l = controladorJPA.consultarRegistro( credencial.getNombreUsuario(), credencial.getContrasena() );
        if ( logged ) {
            FacesContext context = getCurrentInstance();
            context.getExternalContext().getSessionMap().put( "usuario", l );
            intentos = 0;
            char rol = l.getAdministrador();
            if ( rol == 'S' ) {
                return "PrincipalAdministradorIH?faces-redirect=true";
            }
            else {
                return "PrincipalUsuarioIH?faces-redirect=true";
            }
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
     * Finaliza la sesion actual.
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
            return "ErrorIH?faces-redirect=true";
        }
    }
    
    /**
     * Devuelve la lista de preguntas de la base de datos.
     * @return <code>List<Pregunta></code> - La lista de preguntas de la base de datos.
     */    
    public List<Pregunta> darPreguntas() {
        return controladorJPA.darPreguntas();
    }
    
    /**
     * Devuelve la información de un usuario a partir de su llave promaria.
     * @param idUsuario - La llave primaria del usuario.
     * @return <code>Usuario</code> - La información del usuario.
     */    
    public Usuario darUsuario( Integer idUsuario ) {
        return controladorJPA.darUsuario( idUsuario );
    }
    
    /**
     * Establece la pregunta actual a responder.
     * @param idPregunta - La llave primaria de la pregunta actual.
     * @return <code>String</code> - La direccion de la interfaz para responder.
     */
    public String establecerPregunta( Integer idPregunta ) {
        Pregunta p = new Pregunta();
        p.setIdPregunta( idPregunta );
        FacesContext context = getCurrentInstance();
        context.getExternalContext().getSessionMap().put( "pregunta", p );
        return "AgregaRespuestaIH?faces-redirect=true";
    }
    
    /**
     * Establece la pregunta de la que se desea conocer sus respuestas.
     * @param idPregunta- La llave primaria de la pregunta.
     * @return <code>String</code> - La direccion de la interfaz para visualizar las respuestas.
     */
    public String verRespuestasPregunta( Integer idPregunta ) {
        Pregunta p = new Pregunta();
        p.setIdPregunta( idPregunta );
        FacesContext context = getCurrentInstance();
        context.getExternalContext().getSessionMap().put( "pregunta", p );
        return "VerRespuestasIH?faces-redirect=true";
    }
    
        /**
     * Establece la pregunta de la que se desea conocer sus respuestas.
     * @param idPregunta- La llave primaria de la pregunta.
     * @return <code>String</code> - La direccion de la interfaz para visualizar las respuestas.
     */
    public String verRespuestasPreguntaA( Integer idPregunta ) {
        Pregunta p = new Pregunta();
        p.setIdPregunta( idPregunta );
        FacesContext context = getCurrentInstance();
        context.getExternalContext().getSessionMap().put( "pregunta", p );
        return "VerRespuestasAdministradorIH?faces-redirect=true";
    }
    
    /**
     * Devuelve la lista de respuestas de una pregunta.
     * @return <code>List<Respuesta></code> - La lista de respuestas de la pregunta.
     */    
    public List<Respuesta> darRespuestas( Integer idPregunta ) {
        return controladorJPA.darRespuestas( idPregunta );
    }
}
