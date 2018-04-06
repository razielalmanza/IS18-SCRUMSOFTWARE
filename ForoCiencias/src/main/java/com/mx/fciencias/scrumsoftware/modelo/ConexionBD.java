package com.mx.fciencias.scrumsoftware.modelo;

import com.mx.fciencias.scrumsoftware.modelo.exceptions.NonexistentEntityException;
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
 *  La clase <code>ConexionBD</code> define objetos que permiten manejar el acceso a la
 * base de datos del sistema utilizando JPA.
 *
 * Modificado: martes 27 de marzo de 2018.
 *
 * @author <a href="mailto:luis_lazaro@ciencias.unam.mx">Jose Luis Vazquez Lazaro</a>
 * @version 1.2
 */
public class ConexionBD implements Serializable {

	// Atributos.
	/* Entidad de persistencia */
    private EntityManagerFactory entidad = null;
    

	// Metodos constructores.
    /**
     * Permite crear un objeto de tipo <code>ConexionBD</code> a partir de una entidad de
     * persistencia.
     * @param entidad - La entidad de persistencia.
     */
    public ConexionBD( EntityManagerFactory entidad ) {
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
    public void crear( Credencial credencial ) {
		EntityManager entidad = null;
        try {
            entidad = getEntityManager();
            entidad.getTransaction().begin();
            entidad.persist( credencial );
            entidad.getTransaction().commit();
        }
        finally {
            if ( entidad != null ) {
                entidad.close();
            }
        }
    }

    /**
     * Edita una sesion de usuario a partir de la credencial que se le pasa como parametro.
     * @param credencial - La credencial de usuario.
     */
    public void editar( Credencial credencial ) throws NonexistentEntityException, Exception {
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
            Credencial credencial;
            try {
                credencial = entidad.getReference( Credencial.class, idUsuario );
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
     * @return <code>List<Credencial></code> - Las credenciales encontradas. 
     */
    public List<Credencial> encontrarCredencialEntidades() {
        return encontrarCredencialEntidades( true, -1, -1 );
    }

    /**
     * Encuentra las credenciales de la sesion actual dentro de un rango especifico.
     * @param maximoResultado - EL numero de credenciales.
     * @param primerResultado - La primera credencial.
     * @return <code>List<Credencial></code> - Las credenciales encontradas.
     */
    public List<Credencial> encontrarCredencialEntidades( int maximoResultado, int primerResultado ) {
        return encontrarCredencialEntidades( false, maximoResultado, primerResultado );
    }
	
    /**
     * Metodo auxiliar que permite encontrar las credenciales de la sesion actual.
     */
    private List<Credencial> encontrarCredencialEntidades( boolean todos, int maximoResultado, int primerResultado ) {
        EntityManager entidad = getEntityManager();
        try {
            CriteriaQuery consulta = entidad.getCriteriaBuilder().createQuery();
            consulta.select( consulta.from( Credencial.class ) );
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
     * @return <code>Credencial</code> - La credencial del usuario.
     */
    public Credencial encontrarCredencial( Integer idUsuario ) {
        EntityManager entidad = getEntityManager();
        try {
            return entidad.find( Credencial.class, idUsuario );
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
            Root<Credencial> rt = consulta.from( Credencial.class );
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
        Query q = entidad.createNamedQuery( "Credencial.canLogin" ).setParameter( 1, nombreUsuario ).setParameter( 2, contrasena );
        boolean p =  ( boolean ) q.getSingleResult();
        if ( p ) {
        	System.out.println( "Si estas registrado y tu cuenta esta activada" );
        }
        else {
        	System.out.println( "No estas registrado y tu cuenta no esta activada" );
        }
        
        return ( boolean ) q.getSingleResult();
    }

    /**
     * Consulta la credencial de usuario dentro de la base de datos del sistema.
     * @param nombreUsuario - EL nombre de usuario.
     * @param contrasena - La contraseña de usuario.
     * @return <code>Credencial</code> - La informacion de este usuario.
     */
    public Credencial consultarRegistro( String nombreUsuario, String contrasena ) {
        EntityManager entidad = getEntityManager();
        Query q = entidad.createNamedQuery( "Credencial.findByUsuarioAndPassword" ).setParameter( 1, nombreUsuario ).setParameter( 2, contrasena );
        if ( q.getResultList().isEmpty() ) {
            return null;
        }
        return ( Credencial ) q.getSingleResult();
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
    
    /**
     * Activa usuario
     * @param user - El nombre del usuario a activar en la BD
     * @param 
     * @return <code>void</code> -
     */
    public void activaUsuario(String nombreUsuario) {
        EntityManager entidad = getEntityManager();
        Usuario a = consultarRegistroUsuario(nombreUsuario);
        a.setcuentaVerificada('S');
        entidad.getTransaction().begin();
        a = entidad.merge(a);
        entidad.getTransaction().commit();
       
    }
}
