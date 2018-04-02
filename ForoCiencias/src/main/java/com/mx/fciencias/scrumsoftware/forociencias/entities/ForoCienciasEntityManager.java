package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 * Clase encargada de las operaciones relacionadas con la base de datos.
 */
public class ForoCienciasEntityManager {
    
    /* Objeto encargado de las operaciones relacionadas con la base de datos. */
    @PersistenceContext
    private static EntityManager entityManager;
	
    /* Booleano para garantizar la serialización. */
	private static boolean inTransaction = false;
    
    /**
     * Crea un nuevo manejador. Aunque el uso de la clase 
     * javax.persistence.EntityManager es de modo singleton, esta clase no.
     */
    public ForoCienciasEntityManager() {
        if (entityManager == null) {
            entityManager = Persistence
                .createEntityManagerFactory("forociencias")
                .createEntityManager();
        }
    }
	
    /**
     * Guarda una instancia de una entidad.
     * @param <T> una clase etiquetada con import javax.persistence.Entity.
     * @param row la instancia de esta entidad. 
     */
	public <T> void save(T row){
		if (!inTransaction) {
			entityManager.getTransaction().begin();
		}
		entityManager.persist(row);
		if (!inTransaction) {
			entityManager.getTransaction().commit();
		}
	}
	
    /**
     * Elimina la instancia de la entidad.
     * @param <T> una clase etiquetada con import javax.persistence.Entity.
     * @param row la instancia de esta entidad.
     */
	public <T> void delete(T row){
		if (!inTransaction) {
			entityManager.getTransaction().begin();
		}
		entityManager.remove(row);
		if (!inTransaction) {
			entityManager.getTransaction().commit();
		}
	}
	
    /**
     * Persiste una instancia nueva de una entidad.
     * @param <T> una clase etiquetada con import javax.persistence.Entity.
     * @param row la instancia de esta entidad.
     */
	public <T> void persist(T row) {
		entityManager.persist(row);
	}

    /**
     * Comienza una transacción de recursos si no existía una iniciada.
     */
	public void beginTransaction() {
		inTransaction = true;
		entityManager.getTransaction().begin();
	}
	
    /**
     * Termina la transacción si existía una iniciada; guarda cambios a la base
     * de datos.
     */
	public void endTransaction() {
		if (inTransaction) {
			entityManager.getTransaction().commit();
		}
		inTransaction = false;
	}
	
    /**
     * Retira la transacción actual.
     */
	public void rollBack() {
		inTransaction = false;
		entityManager.getTransaction().rollback();
	}
	
    /**
     * Cierrra el manejador de entidades.
     */
	public void close() {
		entityManager.close();
	}
	
    /**
     * Limpia el contexto de persistencia de todas las instancias de entidades
     * que estaba manejando.
     */
	public void clear() {
		entityManager.clear();
	}
	
    /**
     * Búsqueda por llave primaria.
     * @param <T> una clase etiquetada con import javax.persistence.Entity.
     * @param cl La clase de la entidad.
     * @param id La llave primaria.
     * @return la instancia de la entidad o null si no existe.
     */
	public <T> T find(Class<T> cl, Long id) {
		return entityManager.find(cl, id);
	}
	
    /**
     * Busca todas las preguntas en la base de datos.
     * @return una lista de todas las preguntas guardadas.
     */
	public List<Pregunta> findAllPregunta() {
		TypedQuery queryTotal = 
			entityManager.createNamedQuery
			("Pregunta.findAll", Pregunta.class);
		return queryTotal.getResultList();
	}
		
}
