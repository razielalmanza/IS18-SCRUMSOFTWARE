package com.mx.fciencias.scrumsoftware.modelo;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-30T13:40:13")
@StaticMetamodel(Respuesta.class)
public class Respuesta_ { 

    public static volatile SingularAttribute<Respuesta, String> contenido;
    public static volatile SingularAttribute<Respuesta, Integer> idUsuario;
    public static volatile SingularAttribute<Respuesta, Integer> idRespuesta;
    public static volatile SingularAttribute<Respuesta, Timestamp> fechaRespuesta;
    public static volatile SingularAttribute<Respuesta, Integer> idPregunta;

}