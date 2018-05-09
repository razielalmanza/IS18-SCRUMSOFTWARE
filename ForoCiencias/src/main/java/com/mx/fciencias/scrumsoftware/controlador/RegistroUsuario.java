package com.mx.fciencias.scrumsoftware.controlador;

import java.util.Locale;
import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Usuario;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.vista.RegistroIH;
import javax.persistence.EntityManagerFactory;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import java.util.regex.Pattern;
import java.text.ParseException;
import java.util.regex.Matcher;

 /**
  *  La clase <code>RegistroUsuario</code>. 
  *
  * Creado o modificado: martes 27 de marzo de 2018.
  *
  * @author <a href="mailto:razielmcr1@ciencias.unam.mx"></a>
  * @version 1.3
  */
@ManagedBean
@RequestScoped
public class RegistroUsuario {

    private RegistroIH user;
    private EntityManagerFactory entidad;
    private ConexionBD controladorJPA ;
  
    // Metodos constructores.
    /**
     * Constructor sin parametros.
     * Inicializa la clase
     */
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

  
    /**
     * Valida si el correo es @ciencias.unam.mx.
     * @param email - El correo a validar.
     * @return <code>boolean</code> - Si es ciencias o no.
     */
    public boolean emailValido(String email){
        String cienciasDomain = "ciencias.unam.mx";
        String EMAIL_PATTERN = 
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + Pattern.quote(cienciasDomain) + "$";
        
        Pattern a = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = a.matcher(email);
        return matcher.find();
    }
    
    /**
     * Valida si el usuario ya está en la base de datos.
     * @param usuario - El usuario a checar en la bd.
     * @return <code>boolean</code> - Si ya está en la bd o no.
     */
    public boolean yaExiste(String usuario){
       Usuario l = controladorJPA.consultarRegistroUsuario(usuario);
       boolean logged = l != null;
       return logged;
    }

   /**
     * Inserta en la base de datos el usuario con los datos que recibe del xhtml.
     * @param usuario - Usuario que ingresó en el input.
     * @param contraseña - Contraseña que ingresó en el input.
     * @param Fecha - Fecha que ingresó en el input.
     * @param mail - mail que ingresó en el input.
     * @param genero - genero que ingresó en el input.
     */
   public void inserta(String usuario,String contraseña,java.sql.Date fecha,String mail,String genero) throws ParseException{
        // Crea una nueva credencial que persistirá
       Usuario cred = new Usuario(usuario,mail,contraseña,genero,fecha);
       cred.setNombreUsuario(usuario);
       controladorJPA.registroUsuario(cred);
   }
    /**
     * Envia corre de confirmación al correo después de registro.
     * @param usuario - Usuario al que se le mandará el correo como token.
     * @param mail - mail al que se enviará.
     */
    public void enviaCorreo(String usuario,String mail){
        Mail m = new Mail();
        m.sendMail(usuario,"Confirmación cuenta ForoCiencias",mail);
   }
    
     /**
     * Usado para el botón de registro
     * @return <code>String</code> - La redirección según el input del usuario
     */
   public String addUser() throws ParseException { 
            String usuario = user.getUsuario();
            String contraseña = user.getContraseña();
            java.sql.Date fechaA = new java.sql.Date(user.getFechaNac().getTime());     // Crea el objeto date sql con el de tipo util
            String mail = user.getCorreo();
            String genero = user.getGenero();
            
        if (!(emailValido(mail)) || !contraseña.equals(user.getConfirmacionContraseña())) {
            return "RegistroFallidoIH?faces-redirect=true";
        }else {
            if(yaExiste(usuario)){
                user = null;
                return "RegistroFallidoIH?faces-redirect=true";
               }else{
                inserta(usuario,contraseña,fechaA,mail,genero);
                enviaCorreo(usuario,mail);
                user = null;     
                return "RegistroExitosoIH?faces-redirect=true";
            }
        }    
    }
}
