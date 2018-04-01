package com.mx.fciencias.scrumsoftware.forociencias.beans;

import com.mx.fciencias.scrumsoftware.forociencias.entities.ForoCienciasEntityManager;
import com.mx.fciencias.scrumsoftware.forociencias.entities.Pregunta;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "preguntaBean")
@SessionScoped
public class PreguntaBean {
    
    private ForoCienciasEntityManager entityManager;
    
    public List<Pregunta> getPreguntaList() {
        return (new ForoCienciasEntityManager()).findAllPregunta();
    }
    
}
