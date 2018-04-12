package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Pregunta;
import com.mx.fciencias.scrumsoftware.modelo.Credencial;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.CreaPreguntaIH;
import com.mx.fciencias.scrumsoftware.vista.InicioSesionIHBean;
import java.util.Locale;
import java.sql.Timestamp;
import java.text.ParseException;
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
public class CreaPregunta {

    // Atributos.
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA;
    private CreaPreguntaIH pregunta;

    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public CreaPregunta() {
    	FacesContext.getCurrentInstance().getViewRoot().setLocale( new Locale( "es-Mx" ) );
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
        pregunta = new CreaPreguntaIH();
    }

    // Metodos de acceso y modificacion.
    public CreaPreguntaIH getPregunta() {
        return pregunta;
    }

    public void setPregunta( CreaPreguntaIH nuevaPregunta ) {
        this.pregunta = nuevaPregunta;
    }

    // Metodos de implementacion.   
    public void subir( String titulo, String contenido, Integer idUsuario ) {
        Pregunta p = new Pregunta( titulo, contenido, idUsuario );
        controladorJPA.subirPregunta( p );
    }
    
    public String subirPregunta() { 
        String titulo = pregunta.getTitulo();
        String contenido = pregunta.getContenido();
        //InicioSesionIHBean b = new InicioSesionIHBean();
        //Integer idUsuario = b.getUsuario().getIdUsuario();
        FacesContext context = getCurrentInstance();
        Credencial c = ( Credencial ) context.getExternalContext().getSessionMap().get( "usuario" );
        Integer idUsuario = c.getIdUsuario();
        if ( titulo.isEmpty() || contenido.isEmpty() ) {
            pregunta = null;
            return "CreaPreguntaFalloIH?faces-redirect=true";
        }
        else {
            subir( titulo, contenido, idUsuario );
            pregunta = null;
            return "CreaPreguntaExitoIH?faces-redirect=true";
        }
    }
            
}