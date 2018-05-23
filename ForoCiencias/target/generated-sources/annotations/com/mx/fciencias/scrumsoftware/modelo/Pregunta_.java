package com.mx.fciencias.scrumsoftware.modelo;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-23T00:28:32")
@StaticMetamodel(Pregunta.class)
public class Pregunta_ { 

    public static volatile SingularAttribute<Pregunta, String> contenido;
    public static volatile SingularAttribute<Pregunta, Timestamp> fechaPregunta;
    public static volatile SingularAttribute<Pregunta, Integer> idUsuario;
    public static volatile SingularAttribute<Pregunta, String> titulo;
    public static volatile SingularAttribute<Pregunta, Integer> idPregunta;

}