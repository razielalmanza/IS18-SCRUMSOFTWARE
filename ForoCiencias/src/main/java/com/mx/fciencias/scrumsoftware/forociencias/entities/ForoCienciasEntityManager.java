package com.mx.fciencias.scrumsoftware.forociencias.entities;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.spi.PersistenceProvider;

public class ForoCienciasEntityManager {
        
    @PersistenceContext
    private static EntityManager entityManager;
	
	private boolean inTransaction = false;
    
    public ForoCienciasEntityManager() {
        if (entityManager == null) {
            entityManager= Persistence
                .createEntityManagerFactory("forociencias")
                .createEntityManager();
        }
        //entityManager.
    }
	
	public <T> void save(T row){
		if (!inTransaction) {
			entityManager.getTransaction().begin();
		}
		entityManager.persist(row);
		if (!inTransaction) {
			entityManager.getTransaction().commit();
		}
	}
	
	public <T> void delete(T row){
		if (!inTransaction) {
			entityManager.getTransaction().begin();
		}
		entityManager.remove(row);
		if (!inTransaction) {
			entityManager.getTransaction().commit();
		}
	}
	
	public <T> void persist(T row) {
		entityManager.persist(row);
	}

	public void beginTransaction() {
		inTransaction = true;
		entityManager.getTransaction().begin();
	}
	
	public void endTransaction() {
		if (inTransaction) {
			entityManager.getTransaction().commit();
		}
		inTransaction = false;
	}
	
	public void rollBack() {
		inTransaction = false;
		entityManager.getTransaction().rollback();
	}
	
	public void close() {
		entityManager.close();
	}
	
	public void clear() {
		entityManager.clear();
	}
	
	public <T> T find(Class<T> cl, Long id) {
		return entityManager.find(cl, id);
	}
	
	public List<Pregunta> findAllPregunta() {
		TypedQuery queryTotal = 
			entityManager.createNamedQuery
			("Pregunta.findAll", Pregunta.class);
		return queryTotal.getResultList();
//        Usuario usuario1 = new Usuario();
//        usuario1.setNombreUsuario("Pedro");
//        Usuario usuario2 = new Usuario();
//        usuario2.setNombreUsuario("Ana");
//        Usuario usuario3 = new Usuario();
//        usuario3.setNombreUsuario("Juan");
//        
//        Pregunta pregunta1 = new Pregunta();
//        pregunta1.setTitulo("Título de la pregunta 1");
//        pregunta1.setContenido("Contenido de la pregunta 1");
//        pregunta1.setFechaYHora(new Timestamp(System.currentTimeMillis()));
//        PreguntaPK pk1 = new PreguntaPK();
//        pk1.setUsuario(usuario1);
//        pregunta1.setPreguntaPK(pk1);
//        Pregunta pregunta2 = new Pregunta();
//        pregunta2.setTitulo("Título de la pregunta 2");
//        pregunta2.setContenido("Contenido de la pregunta 2");
//        pregunta2.setFechaYHora(new Timestamp(System.currentTimeMillis()));
//        PreguntaPK pk2 = new PreguntaPK();
//        pk2.setUsuario(usuario2);
//        pregunta2.setPreguntaPK(pk2);
//        Pregunta pregunta3 = new Pregunta();
//        pregunta3.setTitulo("Título de la pregunta 3");
//        pregunta3.setContenido("Contenido de la pregunta 3");
//        pregunta3.setFechaYHora(new Timestamp(System.currentTimeMillis()));
//        PreguntaPK pk3 = new PreguntaPK();
//        pk3.setUsuario(usuario3);
//        pregunta3.setPreguntaPK(pk3);
//        
//        LinkedList<Pregunta> preguntas = new LinkedList<>();
//        preguntas.add(pregunta1);
//        preguntas.add(pregunta2);
//        preguntas.add(pregunta3);
//        return preguntas;
        //return null;
	}
		
}
