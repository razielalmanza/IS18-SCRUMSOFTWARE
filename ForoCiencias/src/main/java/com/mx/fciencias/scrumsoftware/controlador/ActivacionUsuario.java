
package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Usuario;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import java.util.*;
import javax.activation.*;
import javax.faces.application.FacesMessage;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author razie
 */
@ManagedBean
@RequestScoped
public class ActivacionUsuario {
    String usuarioToken;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;

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
    
    public String toText(String encoded){
        
        byte[] decoded = Base64.decodeBase64(encoded);      
        return new String(decoded);
         
    }
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
