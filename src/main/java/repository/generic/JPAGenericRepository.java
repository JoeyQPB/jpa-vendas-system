package repository.generic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import annotations.UniqueValue;
import config.ConnectionJPA;
import domain.interfaces.IPersistence;
import exceptions.RepositoryException;

public abstract class JPAGenericRepository<T extends IPersistence, E extends Serializable> implements IJPAGenericRepository<T, E> {

	private Class<T> classPersistence;
	
	public JPAGenericRepository(Class<T> persistenteClass) {
		this.classPersistence = persistenteClass;
	}
	
	@Override
	public T insert(T entity) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	@Override
	public T select(Long value) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
		T entity = entityManager.find(classPersistence, value);
		entityManager.getTransaction().commit();
		return entity;
	}
	
	@Override
	public T selectByUniqueValue(E value) {
		String fieldName = "";
		Field[] declaredFields = classPersistence.getDeclaredFields();
		for (Field field : declaredFields) {
			if(field.isAnnotationPresent(UniqueValue.class)) {
				fieldName = field.getName();
				break;
			}
		}
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
        TypedQuery<T> query = entityManager.createQuery(
                "SELECT x FROM " + classPersistence.getSimpleName() + " x WHERE x." + fieldName + " = :" + fieldName, classPersistence);
        query.setParameter(fieldName, value);
        T entity = query.getSingleResult();
		entityManager.getTransaction().commit();
		return entity;
	}

	@Override
	public List<T> selectAll() {
		EntityManager entityManager = null;
		try {
			entityManager = ConnectionJPA.getEntityManager();
			entityManager.getTransaction().begin();
			
			// cria uma query específica para o tipo de classe
	        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classPersistence);
	        Root<T> root = criteriaQuery.from(classPersistence);
	        criteriaQuery.select(root);
	        
	        // executa a query
	        TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
	        List<T> resultList = typedQuery.getResultList();
	        
	        
	        // psql way
//			StringBuilder sb = new StringBuilder();
//			sb.append("SELECT obj FROM ");
//			sb.append(this.persistenteClass.getSimpleName());
//			sb.append(" obj");
//			List<T> list = entityManager.createQuery(sb.toString(), this.persistenteClass).getResultList();
			
	        entityManager.getTransaction().commit();
		    return resultList;
		} catch (RepositoryException e) {
		    if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
		} finally {
			ConnectionJPA.closeEntityManager(entityManager);
		}
	}

	@Override
	public T update(T entity) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
		entity = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return entity;
	}

	@Override
	public Boolean delete(T entity) {
		EntityManager entityManager = null;
		try {
			entityManager = ConnectionJPA.getEntityManager();
			entityManager.getTransaction().begin();
			entity = entityManager.merge(entity);			
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
			return true;
		} catch (RepositoryException e) {
		    if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
		} finally {
			ConnectionJPA.closeEntityManager(entityManager);
		}
	}

}
