package com.mx.fciencias.scrumsoftware.modelo.exceptions;

import java.util.ArrayList;
import java.util.List;

/**
 *  La clase <code>IllegalOrphanException</code> define una nueva excepcion.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class IllegalOrphanException extends Exception {

	// Atributos.
	/* Mensajes de la excepcion */
    private List<String> mensajes;
    
	// Metodos constructores.
    /**
     * Permite crear un objeto de tipo <code>IllegalOrphanException</code>.
     * @param mensajes - Lista de mensajes.
     */    
    public IllegalOrphanException( List<String> mensajes ) {
        super( ( mensajes != null && mensajes.size() > 0 ? mensajes.get( 0 ) : null ) );
        if ( mensajes == null ) {
            this.mensajes = new ArrayList<String>();
        }
        else {
            this.mensajes = mensajes;
        }
    }
    
    // Metodos de acceso y modificacion.
    /**
     * Devueleve la lista de mensajes.
     * @return <code>List<String></code> - La lista de mensajes.
     */
    public List<String> getMessages() {
        return mensajes;
    }
}
