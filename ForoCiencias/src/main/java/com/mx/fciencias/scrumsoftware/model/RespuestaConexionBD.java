/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * La clase <code>RespuestaConexionBD</code> define objetos que permiten
 * consultar la base de datos
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:lezama@ciencias.unam.mx">Lezama Hernandez Ximena</a>
 * @version 1.2
 */
/**
 * Definicion de las consultas necesarias para validar el registro de un usuario
 */
@Entity
@Table(catalog = "forociencias", schema = "modeloforo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"respuesta"})})

public class RespuestaConexionBD implements Serializable {

    // Atributos.
    /* Llave primaria de la respuesta dentro de la BD */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer idRespuesta;
    private String respuesta;
    
    // Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    public RespuestaConexionBD() {
    }
    
    /**
     * Permite crear un objeto de tipo <code>RespuestaConexionBD</code> a partir de una Llave primaria.
     * @param idRespuesta - La llave primaria.
     */
    public RespuestaConexionBD( Integer idRespuesta ) {
        this.idRespuesta = idRespuesta;
    }

    /**
     * Permite crear un objeto de tipo <code>SesionConexionBD</code> a partir de una Llave primaria, un
     * nombre de usuario y una contraseña.
     * @param idPregunta - La llave primaria.
     * @param respuesta - respuesta de la pregunta.
     */
    public RespuestaConexionBD( Integer idRespuesta, String respuesta) {
        this.idRespuesta = idRespuesta;
        this.respuesta = respuesta;
    }

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la llave primaria de este objeto.
     * @return <code>Integer</code> - La llave primaria.
     */
    public Integer getIdRespuesta() {
        return idRespuesta;
    }

    /**
     * Cambia la llave primaria de este objeto por el que se pasa como parametro.
     * @param nuevoElemento - El nuevo elemento contenido es este
     * <code>Nodo</code>.
     */
    public void setIdRespuesta( Integer nuevoIdRespuesta ) {
        this.idRespuesta = nuevoIdRespuesta;
    }

    /**
     * Devueleve el nombre de usuario de este objeto.
     * @return <code>String</code> - El nombre de usuario.
     */
    public String getRespuesta() {
        return respuesta;
    }

    /**
     * Cambia el nombre de usuario de este objeto por el que se pasa como parametro.
     * @param nuevoNombreUsuario - El nuevo nombre de usuario.
     */
    public void setRespuesta( String Respuesta ) {
        this.respuesta = Respuesta;
    }
    
    /**
     * Convierte este objeto a cadena.
     * @return <code>String</code> - La representacion en cadena.
     */
    @Override
    public String toString() {
        return "com.mx.fciencias.scrumsoftware.model.model.SesionConexionBD[ idRespuesta=" + idRespuesta + " ]";
    }
}
