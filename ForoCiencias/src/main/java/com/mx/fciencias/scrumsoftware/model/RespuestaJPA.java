/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.model;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * La clase <code>RespuestaJPA</code> define objetos que permiten manejar el
 * acceso a la base de datos del sistema utilizando JPA.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:lezama@ciencias.unam.mx">Ximena Lezama Hernandez</a>
 * @version 1.2
 */
public class RespuestaJPA implements Serializable{

    // Atributos.
    /* Entidad de persistencia */
    private EntityManagerFactory entidad = null;

    /**
     * Permite crear un objeto de tipo <code>RespuestaJPA</code> a partir de una
     * entidad de persistencia.
     *
     * @param entidad - La entidad de persistencia.
     */
    public RespuestaJPA(EntityManagerFactory entidad) {
        this.entidad = entidad;
    }

    // Metodos de acceso y modificacion.
    /**
     * Devueleve la entidad de persistencia de este objeto.
     *
     * @return <code>Integer</code> - La llave primaria.
     */
    public EntityManager getEntityManager() {
        return entidad.createEntityManager();
    }

    
   
}
