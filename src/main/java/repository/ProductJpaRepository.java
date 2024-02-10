package repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.ConnectionJPA;
import domain.Product;
import repository.generic.JPAGenericRepository;

public class ProductJpaRepository extends JPAGenericRepository<Product, String> implements IProductJpaRepository {
	public ProductJpaRepository() {
		super(Product.class);
	}
	
	@Override
	public Product select(String value) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.code = :code", Product.class);
        query.setParameter("code", value);
        Product entity = query.getSingleResult();
		entityManager.getTransaction().commit();
		return entity;
	}
}
