package com.mx.fciencias.scrumsoftware.controlador;

import java.util.Locale;

import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Usuario;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.RegistroIH;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;

/**
 ,*
 ,* @author miguel
 ,*/
@ManagedBean
@RequestScoped
public class RegistroUsuario {

    private RegistroIH user;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;

  public RegistroUsuario() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        user = new RegistroIH();
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
    }
    
    public RegistroIH getUser() {
        return user;
    }

    public void setUser(RegistroIH user) {
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
    
    public boolean yaExiste(String usuario){
       Usuario l = controladorJPA.consultarRegistroUsuario(usuario);
         boolean logged = l != null;
         return logged;
       //return false;
    }

   // Recibe los parametros del usuario a crear en la tabla
   public void inserta(String usuario,String contraseña,String fecha,String mail,String genero) throws ParseException{
       // Crea la fecha de nacimiento TEMPORAL
       String fechaTemp = "2018-04-23";
       Date d = new SimpleDateFormat("yyyy-MM-dd").parse(fechaTemp);
       java.sql.Date sqlDate = new java.sql.Date(d.getTime()); 
       // Crea una nueva credencial que persistirá
       Usuario cred = new Usuario(usuario,mail,contraseña,genero,sqlDate);
       cred.setNombreUsuario(usuario);
       controladorJPA.registroUsuario(cred);
   }
    
    public String addUser() throws ParseException { 
            String usuario = user.getUsuario();
            String contraseña = user.getContraseña();
            String fecha = user.getFechaNac();
            String mail = user.getCorreo();
            String genero = user.getGenero();
            
        if (!(emailValid(mail)) || !contraseña.equals(user.getConfirmacionContraseña())) {
            return "RegistroFallidoIH?faces-redirect=true";
        }else {
            if(yaExiste(usuario)){
                user = null;
                return "RegistroFallidoIH?faces-redirect=true";
               }else{
                inserta(usuario,contraseña,fecha,mail,genero);
                user = null;     
                return "RegistroExitosoIH?faces-redirect=true";
            }
        }
           
    }
       
    }


