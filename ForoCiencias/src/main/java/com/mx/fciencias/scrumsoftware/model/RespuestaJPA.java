/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mx.fciencias.scrumsoftware.model;

import com.mx.fciencias.scrumsoftware.model.exceptions.NonexistentEntityException;
import com.mx.fciencias.scrumsoftware.model.RespuestaConexionBD;
import com.mx.fciencias.scrumsoftware.model.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

    /**
     * Crea una sesion de usuario a partir de la credencial que se le pasa como parametro.
     * @param credencial - La informacion necesaria para que un usuario inicie sesion en el
     * sistema (idUsuario, nombreUsuario y contrasena).
     */
    public void crear( RespuestaConexionBD credencial ) {
		EntityManager entidad = null;
        try {
            entidad = getEntityManager();
            entidad.getTransaction().begin();
            entidad.persist( credencial );
            entidad.getTransaction().commit();
        }
        finally {
            if (entidad != null) {
                entidad.close();
            }
        }
    }
    public void editar( RespuestaConexionBD credencial ) throws NonexistentEntityException, Exception {
        EntityManager entidad = null;
        try {
            entidad = getEntityManager();
            entidad.getTransaction().begin();
            credencial = entidad.merge( credencial );
            entidad.getTransaction().commit();
        }
        catch ( Exception ex ) {
        	String msg = ex.getLocalizedMessage();
            if ( msg == null || msg.length() == 0 ) {
            	Integer idRespuesta = credencial.getIdRespuesta();
                if ( encontrarCredencial( idRespuesta ) == null ) {
                	throw new NonexistentEntityException( "La respuesta con idRespuesta " + idRespuesta + " no existe." );
                }
            }
            throw ex;
        }
        finally {
            if ( entidad != null ) {
                entidad.close();
            }
        }
    }
    
    /**
     * Destruye una sesion de ususario a partir de su llave primaria en la BD del sistema.
     * @param idUsuario - La llave primaria del usuario.
     */
    public void destruir( Integer idRespuesta ) throws NonexistentEntityException {
        EntityManager entidad = null;
        try {
            entidad = getEntityManager();
            entidad.getTransaction().begin();
            RespuestaConexionBD credencial;
            try {
                credencial = entidad.getReference( RespuestaConexionBD.class, idRespuesta );
                credencial.getIdRespuesta();
            }
            catch ( EntityNotFoundException enfe ) {
                throw new NonexistentEntityException( "La respuesta con idRespuesta " + idRespuesta + " no existe.", enfe );
            }
            entidad.remove( credencial );
            entidad.getTransaction().commit();
        }
        finally {
            if ( entidad != null ) {
                entidad.close();
            }
        }
    }
    /**
     * Encuentra las credenciales de la sesion actual.
     * @return <code>List<RespuestaConexionBD></code> - Las credenciales encontradas. 
     */
    public List<RespuestaConexionBD> encontrarCredencialEntidades() {
        return encontrarCredencialEntidades( true, -1, -1 );
    }

    /**
     * Encuentra las credenciales de la sesion actual dentro de un rango especifico.
     * @param maximoResultado - EL numero de credenciales.
     * @param primerResultado - La primera credencial.
     * @return <code>List<RespuestaConexionBD></code> - Las credenciales encontradas.
     */
    public List<RespuestaConexionBD> encontrarCredencialEntidades( int maximoResultado, int primerResultado ) {
        return encontrarCredencialEntidades( false, maximoResultado, primerResultado );
    }
   /**
     * Metodo auxiliar que permite encontrar las credenciales de la sesion actual.
     */
    private List<RespuestaConexionBD> encontrarCredencialEntidades( boolean todos, int maximoResultado, int primerResultado ) {
        EntityManager entidad = getEntityManager();
        try {
            CriteriaQuery consulta = entidad.getCriteriaBuilder().createQuery();
            consulta.select( consulta.from( RespuestaConexionBD.class ) );
            Query q = entidad.createQuery( consulta );
            if ( !todos ) {
                q.setMaxResults( maximoResultado );
                q.setFirstResult( primerResultado );
            }
            return q.getResultList();
        }
        finally {
            entidad.close();
        }
    }

    /**
     * Encuentra la credencial de un usuario a partir de su llave primaria.
     * @param idRespuesta - La llave primaria del usuario.
     * @return <code>SesionConexionBD</code> - La credencial del usuario.
     */
    public RespuestaConexionBD encontrarCredencial( Integer idRespuesta ) {
        EntityManager entidad = getEntityManager();
        try {
            return entidad.find( RespuestaConexionBD.class, idRespuesta );
        }
        finally {
            entidad.close();
        }
    }

    /**
     * Cuenta las veces que ha aparecido la sesion con la credencial actual.
     * @return <code>int</code> - El identificador de la credencial actual.
     */
    public int getConteoCredencial() {
        EntityManager entidad = getEntityManager();
        try {
            CriteriaQuery consulta = entidad.getCriteriaBuilder().createQuery();
            Root<RespuestaConexionBD> rt = consulta.from( RespuestaConexionBD.class );
            consulta.select( entidad.getCriteriaBuilder().count( rt ) );
            Query q = entidad.createQuery( consulta );
            return ( ( Long ) q.getSingleResult() ).intValue();
        }
        finally {
            entidad.close();
        }
    }
   
    
     /**
     * Registra
     * @param user - EL objeto de tipo Usuario (entidad) a persistir
     * @param 
     * @return <code>void</code> -
     */
    public void registroUsuario(Usuario user) {
        EntityManager entidad = getEntityManager();
        entidad.getTransaction().begin();
        entidad.persist(user);
        entidad.getTransaction().commit();
       
    }
    
}
