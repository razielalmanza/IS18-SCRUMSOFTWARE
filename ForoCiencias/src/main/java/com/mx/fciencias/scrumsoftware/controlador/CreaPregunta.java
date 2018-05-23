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
 *  La clase <code>CrearPregunta</code> 
 *
 * Creado o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
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
    public String subir( String titulo, String contenido, Integer idUsuario, char rol ) {
        try {
            Pregunta p = new Pregunta( titulo, contenido, idUsuario );
            controladorJPA.subirPregunta( p );
            if ( rol == 'S' ) {
                return "CreaPreguntaExitosoAIH?faces-redirect=true";
            }
            else {
                return "CreaPreguntaExitosoIH?faces-redirect=true";
            }
        }
        catch ( PersistenceException e ) {
            return "ErrorIH?faces-redirect=true";
        }
    }
    
    public String subirPregunta() { 
        String titulo = pregunta.getTitulo();
        System.out.println( "punto de prueba" );
        String contenido = pregunta.getContenido();
        FacesContext context = getCurrentInstance();
        Credencial c = ( Credencial ) context.getExternalContext().getSessionMap().get( "usuario" );
        Integer idUsuario = c.getIdUsuario();
        char rol = c.getAdministrador();
        if ( titulo.isEmpty() ) {
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "La pregunta debe tener un título.", "" ) );
            return null;
        }
        else {
            if( contenido.isEmpty() ) {
                FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "La pregunta debe tener un contenido.", "" ) );
                return null;
            }
            else {
                String s = subir( titulo, contenido, idUsuario, rol );
                return s;
            }
        }
    }    
      
    public String eliminarPregunta( String idRespuesta ) {
        try {
            System.out.println( "Punto de control" );
            controladorJPA.eliminarPregunta( idRespuesta );
            return "EliminaPreguntaExitosoIH?faces-redirect=true";
        }
        catch ( PersistenceException e ) {
            return "ErrorIH?faces-redirect=true";
        }
    }
}
