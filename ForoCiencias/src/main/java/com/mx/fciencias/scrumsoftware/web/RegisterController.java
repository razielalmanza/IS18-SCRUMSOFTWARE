package com.mx.fciencias.scrumsoftware.web;

import java.util.Locale;

import com.mx.fciencias.scrumsoftware.model.SesionJPA;
import com.mx.fciencias.scrumsoftware.model.SesionConexionBD;
import com.mx.fciencias.scrumsoftware.model.ProveedorEntidadPersistencia;
import javax.persistence.EntityManagerFactory;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 ,*
 ,* @author miguel
 ,*/
@ManagedBean
@RequestScoped
public class RegisterController {

    private Usuario user;
    private EntityManagerFactory entidad;
    private SesionJPA controladorJPA ;

  public RegisterController() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        user = new Usuario();
        //entidad = ProveedorEntidadPersistencia.proveer();
        //controladorJPA = new SesionJPA( entidad );
    }
    
    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

  
    // Valida el mail
    public boolean emailValid(String email){
        String cienciasDomain = "ciencias.unam.mx";
        String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + Pattern.quote(cienciasDomain) + "$";
        
        Pattern a = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = a.matcher(email);
        return matcher.find();
    }
    
   public boolean yaExisteUsuario(){
       // return controladorJPA.consultarRegistroUsuario( user.getUsuario() );
       return false;
   }
   
   //public void inserta(){
     //  controladorJPA.registroUsuario(user.getUsuario(),user.getContraseña());
   //}
    
    public String addUser() { 
            
        if (!(emailValid(user.getCorreo())) || !user.getContraseña().equals(user.getConfirmacionContraseña())) {
            return "RegistroFallidoIH?faces-redirect=true";
        }else {
           if(yaExisteUsuario()){
                user = null;
               return "RegistroFallidoIH?faces-redirect=true";
           }else{
                user = null;
                //inserta();
            return "RegistroExitosoIH?faces-redirect=true";
           }  
           
        }
       
    }

}
