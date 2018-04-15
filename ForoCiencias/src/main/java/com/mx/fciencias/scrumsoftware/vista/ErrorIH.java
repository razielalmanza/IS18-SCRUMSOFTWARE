package com.mx.fciencias.scrumsoftware.vista;

/**
 *  La clase <code>ErrorIH</code> define objetos que permiten manejar los errores del
 * sistema.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.1
 */
public final class ErrorIH {

	// Atributos.
	/* Mensaje de error */
    private String mensajeError;
    
    // Metodo contructor.
    /**
     * Crea un objeto <code>ErrorIH</code> a partir del parametro dado.
     * @return <code>String</code> - El nombre de usuario.
     */
    public ErrorIH( String mensajeError ) {
        this.mensajeError = mensajeError;
    }

	// Metodos de implementacion.
    /**
     * Muestra el mensaje de error.
     * @return <code>String</code> - El mensaje de error.
     */
    public String mostrarMensaje() {
        return mensajeError;
    }
}
