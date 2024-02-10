package services;

import domain.Product;
import repository.IProductJpaRepository;

public class ProductService extends GenericService<Product, Long> implements IProductService {

	private IProductJpaRepository repository;
	
	public ProductService(IProductJpaRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Product getByCode(String code) {
		return repository.selectByCode(code);
	}
}
