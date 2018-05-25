/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.controlador;

import com.mx.fciencias.scrumsoftware.modelo.ConexionBD;
import com.mx.fciencias.scrumsoftware.modelo.Pregunta;
import com.mx.fciencias.scrumsoftware.modelo.ProveedorEntidadPersistencia;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManagerFactory;

/**
 * Bean para manejar el query dado por el usuario para hacer una búsqueda.
 * @author Marco Olea
 */
@ManagedBean
@SessionScoped
public class BusquedaContenido {
    /* Entidad para la persistencia de una sesion de usuario */
    private EntityManagerFactory entidad;
    /* Sesión de usuario. */
    private ConexionBD controladorJPA;
    /* Parámetro dado por el usuario en la página principal. */
    private String query;

    /**
     * Inicializa la sesión de usuario.
     */
    public BusquedaContenido() {
        entidad = ProveedorEntidadPersistencia.proveer();
        controladorJPA = new ConexionBD( entidad );
    }
    
    /**
     * Devuelve la lista de preguntas de la base de datos.
     * @return <code>List<Pregunta></code> - La lista de preguntas de la base de datos.
     */    
    public List<Pregunta> darPreguntasPorTitulo(String titulo) {
        return controladorJPA.darPreguntasPorTitulo(titulo);
    }
    
    /**
     * Regresa el query del usuario.
     * @return el parámetro dado por el usuario.
     */
    public String getQuery() {
        return query;
    }
    
    /**
     * Fija el query del usuario.
     * @param query el parámetro dado por el usuario.
     */
    public void setQuery(String query) {
        this.query = query;
    }
    
}
