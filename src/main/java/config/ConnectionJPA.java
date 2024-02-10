package config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ConnectionJPA {

	private static EntityManagerFactory entityManagerFactory = null;
	private static EntityManager entityManager = null;
	
	private ConnectionJPA() {}
	
	public static EntityManager getEntityManager() {
		if (entityManager == null || !entityManager.isOpen()) entityManager = initEntityManager();
		return entityManager;
	}
	private static EntityManager initEntityManager() {
		entityManagerFactory = Persistence.createEntityManagerFactory("JPA-vendas");
		return entityManagerFactory.createEntityManager();
	}
	
	public static void closeEntityManager(EntityManager eM) {
		if (eM != null && eM.isOpen()) {
			entityManagerFactory.close();
			eM.close();
		}
	}
}
