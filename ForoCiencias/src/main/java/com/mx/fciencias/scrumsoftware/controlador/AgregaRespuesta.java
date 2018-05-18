package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Respuesta;
import com.mx.fciencias.scrumsoftware.modelo.Pregunta;
import com.mx.fciencias.scrumsoftware.modelo.Credencial;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.AgregaRespuestaIH;
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
 *  La clase <code>AgregaRespuesta</code>.
 *
 * Creado o modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:"></a>
 * @version 1.1
 */
@ManagedBean
@SessionScoped
public class AgregaRespuesta {

    // Atributos.
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA;
    private AgregaRespuestaIH respuesta;

    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public AgregaRespuesta() {
    	FacesContext.getCurrentInstance().getViewRoot().setLocale( new Locale( "es-Mx" ) );
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
        respuesta = new AgregaRespuestaIH();
    }

    // Metodos de acceso y modificacion.
    public AgregaRespuestaIH getRespuesta() {
        return respuesta;
    }

    public void setRespuesta( AgregaRespuestaIH nuevaRespuesta ) {
        this.respuesta = nuevaRespuesta;
    }
    
    // Metodos de implementacion.    
    public String enviar( String contenido, Integer idPregunta, Integer idUsuario, char rol ) {
        try {
            Respuesta r = new Respuesta( contenido, idPregunta, idUsuario );
            controladorJPA.enviarRespuesta( r );
            if ( rol == 'S' ) {
                System.out.println( "Admin" );
                return "AgregaRespuestaExitosoAIH?faces-redirect=true";    
            }
            else {
                return "AgregaRespuestaExitosoIH?faces-redirect=true";
            }
        }
        catch ( PersistenceException e ) {
        	return "ErrorIH?faces-redirect=true";
        }
    }
    
    public String enviarRespuesta() { 
        String contenido = respuesta.getContenido();
        FacesContext context = getCurrentInstance();
        Credencial c = ( Credencial ) context.getExternalContext().getSessionMap().get( "usuario" );
        Pregunta p = ( Pregunta ) context.getExternalContext().getSessionMap().get( "pregunta" );
        Integer idPregunta = p.getIdPregunta();
        Integer idUsuario = c.getIdUsuario();
        char rol = c.getAdministrador();
        if ( contenido.isEmpty() ) {
            FacesContext.getCurrentInstance().addMessage( null, new FacesMessage( FacesMessage.SEVERITY_ERROR, "La respuesta no puede ser vacía.", "" ) );
            return null;
        }
        else {
            String s = enviar( contenido, idPregunta, idUsuario, rol );
            return s;
        }
    }      
}
