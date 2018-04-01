package com.mx.fciencias.scrumsoftware.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *  La clase <code>EntityProvider</code> define objetos que permiten crear una pagina de sesion
 * de usuario.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class ProveedorEntidadPersistencia {

	// Atributos.
	/* Entidad de persistencia */
    private static EntityManagerFactory entidadPersistencia;

	// Metodos constructores.
    /**
     * Constructor sin parametros.
     */
    private ProveedorEntidadPersistencia() {
    }

    // Metodos de implementacion.
    /**
     * Devueleve una unidad de persistencia de sesion de usuario.
     * @return <code>EntityManagerFactory</code> - La unidad de persistencia.
     */
    public static EntityManagerFactory proveer() {
        if ( entidadPersistencia == null ) {
        	entidadPersistencia = Persistence.createEntityManagerFactory( "PersistenceUnit" );
        }
        return entidadPersistencia;
    }
}
