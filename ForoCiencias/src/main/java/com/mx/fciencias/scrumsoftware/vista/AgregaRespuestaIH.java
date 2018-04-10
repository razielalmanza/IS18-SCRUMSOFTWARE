/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.vista;
import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import com.mx.fciencias.scrumsoftware.modelo.Respuesta;
import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManagerFactory;


/**
 *
 * @author ximenalezama
 */
@ManagedBean(name="agregaRespuestaIH")
@SessionScoped
public class AgregaRespuestaIH {
    
    private EntityManagerFactory entidad;
    private ConexionBD respuestaJPA ;
    private String contenido;
    private Integer id;
    private Integer user;

    
    public String getContenido() {
        return contenido;
    }
    
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    
    public AgregaRespuestaIH(){
        entidad = ProveedorEntidadPersistencia.proveer();
        respuestaJPA = new ConexionBD(entidad);
    }

    // Metodos de implementacion.
    /**
     * Inicializa la sesion de usuario a partir de la credencial identificada.
     * @return <code>String</code> - La direccion de la interfaz de usuario.
     */
    public String agrega () throws ValidatorException, ParseException{
        if (contenido.equals("")){
            return "ErrorAgregarRespIH?faces-redirect=true";
        }else {
            inserta(contenido);
            return "RespuestaExitoIH?faces-redirect=true";
        }
    }
    
    public void inserta(String contenido) throws ParseException{
       // Crea la fecha
       java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
       Respuesta resp = new Respuesta(contenido, sqlDate);
       respuestaJPA.registroRespuesta(resp);
   }

}
