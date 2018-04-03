package com.mx.fciencias.scrumsoftware.web;

import java.util.Locale;

import com.mx.fciencias.scrumsoftware.model.SesionJPA;
import com.mx.fciencias.scrumsoftware.model.Usuario;
import com.mx.fciencias.scrumsoftware.model.SesionConexionBD;
import com.mx.fciencias.scrumsoftware.model.ProveedorEntidadPersistencia;
import javax.persistence.EntityManagerFactory;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.regex.Pattern;
import com.mx.fciencias.scrumsoftware.model.SesionConexionBD;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.text.SimpleDateFormat;

/**
 ,*
 ,* @author miguel
 ,*/
@ManagedBean
@RequestScoped
public class RegisterController {

    private UsuarioA user;
    private EntityManagerFactory entidad;
    private SesionJPA controladorJPA ;

  public RegisterController() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale("es-Mx"));
        user = new UsuarioA();
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new SesionJPA( entidad );
    }
    
    public UsuarioA getUser() {
        return user;
    }

    public void setUser(UsuarioA user) {
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
    
    public boolean yaExiste(String user){
       // return controladorJPA.consultarRegistroUsuario(user);
       return false;
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


