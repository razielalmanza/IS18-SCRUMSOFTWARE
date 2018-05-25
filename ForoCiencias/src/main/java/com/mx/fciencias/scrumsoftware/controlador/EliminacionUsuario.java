
package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Usuario;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.faces.application.FacesMessage;
import org.apache.commons.codec.binary.Base64;

/**
- *  La clase <code>ActivacionUsuario</code>.
  *
- * Creado o modificado: martes 27 de marzo de 2018.
- *
- * @author <a href="mailto:razielmcr1@ciencias.unam.mx"></a>
- * @version 1.1
+ * 
  */
@ManagedBean
@RequestScoped
public class EliminacionUsuario {
    String usuario;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;

     // Metodos constructores.
    /**
     * Constructor sin parametros.
     * Inicializa la clase
     */
  public EliminacionUsuario() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Usado para el botón de activar registro
     * @return <code>String</code> - La redirección según el input del usuario
     */
    public String eliminaUsuario(){
        Usuario l = controladorJPA.consultarRegistroUsuario(usuario);
        boolean existe = l != null;
        if(existe){
        // elimina
            controladorJPA.eliminarUsuario(usuario);
            FacesContext.getCurrentInstance().addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario borrado con sus preguntas y respuestas.", ""));
        }else{
            FacesContext.getCurrentInstance().addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este usuario no existe en la BD " + usuario, ""));
            
        }
        return null;
    }  
}
