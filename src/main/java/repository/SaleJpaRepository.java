package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import config.ConnectionJPA;
import domain.Client;
import domain.Product;
import domain.ProductQuantity;
import domain.Sale;
import exceptions.RepositoryException;
import repository.generic.JPAGenericRepository;

public class SaleJpaRepository extends JPAGenericRepository<Sale, String> implements ISaleJpaRepository {

	public SaleJpaRepository() {
		super(Sale.class);
	}

	@Override
	public void finalizeSale(Sale sale) {
		super.update(sale);
		
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		for ( ProductQuantity prod : sale.getproducts()) {
			Product product = prod.getproduct();
			product.setQuantity(product.getQuantity() - prod.getquantity());
			
			
			Product prodJpa = entityManager.merge(product);
			ProductJpaRepository productJpaRepository = new ProductJpaRepository();
			productJpaRepository.update(prodJpa);
		}
	}

	@Override
	public void cancelSale(Sale sale) {
		super.update(sale);
	}
	
	@Override
	public Boolean delete(Sale sale) {
		throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
	}
	
	@Override
	public Sale insert(Sale entity){
		EntityManager entityManager = null;
		try {
			entityManager = ConnectionJPA.getEntityManager();
			
			for ( ProductQuantity prod : entity.getproducts()) {
				Product prodJpa = entityManager.merge(prod.getproduct());
				prod.setproduct(prodJpa);
			}
			
			Client clienntJpa = entityManager.merge(entity.getclient());
			entity.setclient(clienntJpa);
			entityManager.persist(entity);
			
			entityManager.getTransaction().commit();
			
			return entity;
		} catch (RepositoryException e) {
		    if (entityManager.getTransaction().isActive()) entityManager.getTransaction().rollback();
			e.printStackTrace();
			throw new RepositoryException(e.getMessage());
		} finally {
			ConnectionJPA.closeEntityManager(entityManager);
		}
	}
	
	@Override
	public Sale selectWithCollection(String code) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Sale> query = builder.createQuery(Sale.class);
		Root<Sale> root = query.from(Sale.class);
		root.fetch("client");
		root.fetch("products");
		query.select(root).where(builder.equal(root.get("code"), code));
		TypedQuery<Sale> tpQuery = entityManager.createQuery(query);
		Sale sale = tpQuery.getSingleResult();   
		
		entityManager.getTransaction().commit();
		ConnectionJPA.closeEntityManager(entityManager);
		
		return sale;
	}

	@Override
	public void cleanDB() {
		List<Sale> sales = this.selectAll();
		for (Sale sale : sales) {
			super.delete(sale);
		}
	}
}
