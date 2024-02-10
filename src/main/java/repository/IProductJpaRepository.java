package repository;

import domain.Product;
import repository.generic.IJPAGenericRepository;

public interface IProductJpaRepository extends IJPAGenericRepository<Product, Long>{

	public Product selectByCode(String code);
}
