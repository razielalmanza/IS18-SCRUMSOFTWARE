package com.mx.fciencias.scrumsoftware.model;

import com.mx.fciencias.scrumsoftware.model.exceptions.NonexistentEntityException;
import com.mx.fciencias.scrumsoftware.model.SesionConexionBD;
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
 *  La clase <code>SesionJPA</code> define objetos que permiten manejar el acceso a la
 * base de datos del sistema utilizando JPA.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
 */
public class SesionJPA implements Serializable {

	// Atributos.
	/* Entidad de persistencia */
    private EntityManagerFactory entidad = null;
    

	// Metodos constructores.
    /**
     * Permite crear un objeto de tipo <code>SesionJPA</code> a partir de una entidad de
     * persistencia.
     * @param entidad - La entidad de persistencia.
     */
    public SesionJPA( EntityManagerFactory entidad ) {
        this.entidad = entidad;
    }
    
    // Metodos de acceso y modificacion.
    /**
     * Devueleve la entidad de persistencia de este objeto.
     * @return <code>Integer</code> - La llave primaria.
     */
    public EntityManager getEntityManager() {
        return entidad.createEntityManager();
    }

	// Metodos de implementacion.
    /**
     * Crea una sesion de usuario a partir de la credencial que se le pasa como parametro.
     * @param credencial - La informacion necesaria para que un usuario inicie sesion en el
     * sistema (idUsuario, nombreUsuario y contrasena).
     */
    public void crear( SesionConexionBD credencial ) {
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

    /**
     * Edita una sesion de usuario a partir de la credencial que se le pasa como parametro.
     * @param credencial - La credencial de usuario.
     */
    public void editar( SesionConexionBD credencial ) throws NonexistentEntityException, Exception {
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
            	Integer idUsuario = credencial.getIdUsuario();
                if ( encontrarCredencial( idUsuario ) == null ) {
                	throw new NonexistentEntityException( "La sesión con idUsuario " + idUsuario + " no existe." );
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
    public void destruir( Integer idUsuario ) throws NonexistentEntityException {
        EntityManager entidad = null;
        try {
            entidad = getEntityManager();
            entidad.getTransaction().begin();
            SesionConexionBD credencial;
            try {
                credencial = entidad.getReference( SesionConexionBD.class, idUsuario );
                credencial.getIdUsuario();
            }
            catch ( EntityNotFoundException enfe ) {
                throw new NonexistentEntityException( "La sesión con idUsuario " + idUsuario + " no existe.", enfe );
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
     * @return <code>List<SesionConexionBD></code> - Las credenciales encontradas. 
     */
    public List<SesionConexionBD> encontrarCredencialEntidades() {
        return encontrarCredencialEntidades( true, -1, -1 );
    }

    /**
     * Encuentra las credenciales de la sesion actual dentro de un rango especifico.
     * @param maximoResultado - EL numero de credenciales.
     * @param primerResultado - La primera credencial.
     * @return <code>List<SesionConexionBD></code> - Las credenciales encontradas.
     */
    public List<SesionConexionBD> encontrarCredencialEntidades( int maximoResultado, int primerResultado ) {
        return encontrarCredencialEntidades( false, maximoResultado, primerResultado );
    }
	
    /**
     * Metodo auxiliar que permite encontrar las credenciales de la sesion actual.
     */
    private List<SesionConexionBD> encontrarCredencialEntidades( boolean todos, int maximoResultado, int primerResultado ) {
        EntityManager entidad = getEntityManager();
        try {
            CriteriaQuery consulta = entidad.getCriteriaBuilder().createQuery();
            consulta.select( consulta.from( SesionConexionBD.class ) );
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
     * @param idUsuario - La llave primaria del usuario.
     * @return <code>SesionConexionBD</code> - La credencial del usuario.
     */
    public SesionConexionBD encontrarCredencial( Integer idUsuario ) {
        EntityManager entidad = getEntityManager();
        try {
            return entidad.find( SesionConexionBD.class, idUsuario );
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
            Root<SesionConexionBD> rt = consulta.from( SesionConexionBD.class );
            consulta.select( entidad.getCriteriaBuilder().count( rt ) );
            Query q = entidad.createQuery( consulta );
            return ( ( Long ) q.getSingleResult() ).intValue();
        }
        finally {
            entidad.close();
        }
    }

    /**
     * Determina si el usuario puede iniciar sesion con la credencial indicada.
     * @param nombreUsuario - EL nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     * @return <code>boolean</code> - true si existen las credenciales en la BD del
     * sistema, false en otro caso.
     */
    public boolean estaRegistrado( String nombreUsuario, String contrasena ) {
        EntityManager entidad = getEntityManager();
        Query q = entidad.createNamedQuery( "SesionConexionBD.canLogin" ).setParameter( 1, nombreUsuario ).setParameter( 2, contrasena );
        return ( boolean ) q.getSingleResult();
    }

    /**
     * Consulta la credencial de usuario dentro de la base de datos del sistema.
     * @param nombreUsuario - EL nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     * @return <code>SesionCOnexionBD</code> - La informacion de este usuario.
     */
    public SesionConexionBD consultarRegistro( String nombreUsuario, String contrasena ) {
        EntityManager entidad = getEntityManager();
        Query q = entidad.createNamedQuery( "SesionConexionBD.findByUsuarioAndPassword" ).setParameter( 1, nombreUsuario ).setParameter( 2, contrasena );
        if ( q.getResultList().isEmpty() ) {
            return null;
        }
        return ( SesionConexionBD ) q.getSingleResult();
    }
    
    /**
     * Consulta si ya existe la credencial de usuario dentro de la base de datos del sistema.
     * @param nombreUsuario - EL nombre de usuario.
     * @return <code>boolean</code> - si ya existe en la DB.
     */
     public Usuario consultarRegistroUsuario( String nombreUsuario ) {
        EntityManager entidad = getEntityManager();
        TypedQuery<Usuario> q = entidad.createNamedQuery( "Usuario.findByUsuario",Usuario.class ).setParameter("nombreUsuario", nombreUsuario);
        if ( q.getResultList().isEmpty() ) {
            return null;
        }
        return ( Usuario ) q.getSingleResult();
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
