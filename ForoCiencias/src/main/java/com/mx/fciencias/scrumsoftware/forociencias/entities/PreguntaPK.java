package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class PreguntaPK implements Serializable {
    
    public PreguntaPK(){}
    
    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;
    
    @Column(name="id_pregunta")
    private Long idPregunta;

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getIdPregunta() {
        return idPregunta;
    }

    public void setIdPregunta(Long idPregunta) {
        this.idPregunta = idPregunta;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPregunta != null) ? idPregunta.hashCode() : 0;
        hash += (usuario.getIdUsuario() != null) ? usuario.getIdUsuario().hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PreguntaPK)) {
            return false;
        }
        PreguntaPK other = (PreguntaPK) object;
        return this.idPregunta.equals(other.idPregunta) &&
            this.usuario.getIdUsuario().equals(other.usuario.getIdUsuario());
    }
    
}
