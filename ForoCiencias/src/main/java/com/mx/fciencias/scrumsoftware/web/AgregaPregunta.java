/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.web;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author ximenalezama
 */
@ManagedBean
@SessionScoped
public class AgregaPregunta {
    
    private String respuesta;
    
    public String getRespuesta() {
        return respuesta;
    }
    
    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }
    

    // Metodos de implementacion.
    /**
     * Inicializa la sesion de usuario a partir de la credencial identificada.
     *
     * @param arg0
     * @param arg1
     * @param arg2
     * @return <code>String</code> - La direccion de la interfaz de usuario.
     */
    public String agrega () throws ValidatorException{
        if (respuesta.length() == 0){
            return "RespuestaExitoIH?faces-redirect=true";
        }
        return "ErrorAgregarRespIH?faces-redirect=true";
        
    }

}