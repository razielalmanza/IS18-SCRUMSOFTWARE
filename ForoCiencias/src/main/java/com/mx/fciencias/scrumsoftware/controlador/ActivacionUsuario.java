
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
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author razie
 */
@ManagedBean
@RequestScoped
public class ActivacionUsuario {
    String usuario;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;

  public ActivacionUsuario() {
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
    

    public String activaUsuario(){
       
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
