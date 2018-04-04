package com.mx.fciencias.scrumsoftware.modelo.exceptions;

/**
 *  La clase <code>NonexistentEntityException</code> define una nueva excepcion.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.0
 */
public class NonexistentEntityException extends Exception {

	// Metodos constructores.
    /**
     * Permite crear un objeto de tipo <code>NonexistentEntityException</code>.
     * @param mensaje - El mensaje.
     * @param causa - La causa del efecto.
     */ 
    public NonexistentEntityException( String mensaje, Throwable causa ) {
        super( mensaje, causa );
    }
    
    /**
     * Permite crear un objeto de tipo <code>NonexistentEntityException</code>.
     * @param mensaje - El mensaje.
     */ 
    public NonexistentEntityException( String mensaje ) {
        super( mensaje );
    }
}
