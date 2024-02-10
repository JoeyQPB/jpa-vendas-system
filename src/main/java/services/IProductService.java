package services;

import domain.Product;

public interface IProductService extends IGenericService<Product, Long> {

	public Product getByCode(String code);
}
