package repository;

import domain.Product;
import repository.generic.JPAGenericRepository;

public class ProductJpaRepository extends JPAGenericRepository<Product, String> implements IProductJpaRepository {
	public ProductJpaRepository() {
		super(Product.class);
	}
}
