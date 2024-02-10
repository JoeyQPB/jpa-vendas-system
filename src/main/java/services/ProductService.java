package services;

import domain.Product;
import repository.IProductJpaRepository;

public class ProductService extends GenericService<Product, String> implements IProductService {
	public ProductService(IProductJpaRepository repository) {
		super(repository);
	}
}
