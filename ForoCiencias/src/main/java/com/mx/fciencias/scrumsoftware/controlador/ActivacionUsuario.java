
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
public class ActivacionUsuario {
    String usuarioToken;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;

     // Metodos constructores.
    /**
     * Constructor sin parametros.
     * Inicializa la clase
     */
  public ActivacionUsuario() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
    }
    
    public String getUsuarioToken() {
        return usuarioToken;
    }

    public void setUsuarioToken(String usuarioToken) {
        this.usuarioToken = usuarioToken;
    }
    
    /**
     * Usado para pasar de Base64 a Texto
     * @return <code>String</code> - El usuario que ingresaron en formato normal.
     */
    public String toText(String encoded){
        byte[] decoded = Base64.decodeBase64(encoded);      
        return new String(decoded);
         
    }
    
    /**
     * Usado para el botón de activar registro
     * @return <code>String</code> - La redirección según el input del usuario
     */
    public String activaUsuario(){
         String usuario = toText(usuarioToken);
         Usuario l = controladorJPA.consultarRegistroUsuario(usuario);
         boolean logged = l != null;
        if(logged){
        // activa
            controladorJPA.activaUsuario(usuario);
            FacesContext.getCurrentInstance().addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_INFO, "Cuenta activada. Ya puedes inicar sesión.", ""));
        }else{
            FacesContext.getCurrentInstance().addMessage(null
                                                         , new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este usuario no existe en la BD", ""));

        }
        return null;
    }
    
}
