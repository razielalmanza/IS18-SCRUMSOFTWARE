package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Clase que representa la llave primaria de la entidad preguntas.pregunta.
 * @author Marco Olea
 */
@Embeddable
public class PreguntaPK implements Serializable {
    
    /**
     * Constructor público sin argumentos requerido.
     */
    public PreguntaPK() {}
    
    /* El usuario asociado a esta pregunta. */
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    /* 
     * El identificador de esta pregunta con respecto a su usuario (no  
     * necesariamente único).
     */
    @Column(name="id_pregunta")
    private Long idPregunta;

    /**
     * Regresa el usuario asociado a esta pregunta.
     * @return un Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Fija el nuevo usuario asociado a esta pregunta.
     * @param usuario el nuevo Usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Regresa el identificador de esta pregunta.
     * @return un Long que representa el identificador.
     */
    public Long getIdPregunta() {
        return idPregunta;
    }

    /**
     * Fija un nuevo identificador para esta pregunta.
     * @param idPregunta el nuevo identificador.
     */
    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }
    
    /**
     * Método que debe ser sobreescrito por una clase etiquetada por
     * javax.persistence.Embeddable.
     * @return si idPregunta y idUsuario no son nulos, regresa la suma de sus 
     * respectivas llamadas a la función hashCode(). Si alguno es nulo, se les 
     * "asigna" un hashcode de cero.
     */
    @Override
    public int hashCode() {
        int hash = (idPregunta == null) ? 0 : idPregunta.hashCode();
        hash += (usuario.getIdUsuario() == null) ? 0 : usuario.getIdUsuario().hashCode();
        return hash;
    }
    
    /**
     * Método que debe ser sobreescrito por una clase etiquetada por
     * javax.persistence.Embeddable.
     * @return true si los dos objetos tienen el mismo idPregunta y idUsuario.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object instanceof PreguntaPK)) {
            return false;
        }
        PreguntaPK other = (PreguntaPK) object;
        if (this.idPregunta == null || other.idPregunta == null ||
            this.usuario == null || other.usuario == null) {
            return false;
        }
        return this.idPregunta.equals(other.idPregunta) &&
            this.usuario.getIdUsuario().equals(other.usuario.getIdUsuario());
    }
    
}
