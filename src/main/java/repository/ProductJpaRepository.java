package repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.ConnectionJPA;
import domain.Product;
import repository.generic.JPAGenericRepository;

public class ProductJpaRepository extends JPAGenericRepository<Product, Long> implements IProductJpaRepository {
	public ProductJpaRepository() {
		super(Product.class);
	}
	
	@Override
	public Product selectByCode(String code) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
        TypedQuery<Product> query = entityManager.createQuery(
                "SELECT p FROM Product p WHERE p.code = :code", Product.class);
        query.setParameter("code", code);
        Product entity = query.getSingleResult();
		entityManager.getTransaction().commit();
		return entity;
	}
}
