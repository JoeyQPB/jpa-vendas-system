package repositoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Product;
import domain.factory.EntityFactory;
import exceptions.RepositoryException;
import repository.IProductJpaRepository;
import repository.ProductJpaRepository;

public class ProductRepositoryTest {

	private IProductJpaRepository productJpaRepository;
	private EntityFactory entityFactory;
	private Product product;
	private Product productDB;

	public ProductRepositoryTest() {
		this.productJpaRepository = new ProductJpaRepository();
		this.entityFactory = new EntityFactory();
	}

	@Before
	public void init() {
		clearDB();
		this.product = entityFactory.getNewProduct("PROD-01-TEST");
		this.productDB = productJpaRepository.insert(product);
	}

	@After
	public void end() {
		clearDB();
	}

	@Test
	public void insertProductTest() {
		assertNotNull(productDB);
		assertNotNull(productDB.getId());
		assertEquals(productDB, product);
	}

	@Test
	public void selectProductTest() {
		Product productTestSelect = productJpaRepository.select(product.getCode());
		assertNotNull(productTestSelect);
		assertNotNull(productTestSelect.getId());
		assertEquals(productTestSelect, product);
	}

	@Test
	public void selectAllProductTest() {
		Product p2 = entityFactory.getNewProduct("PROD-02-TEST");
		Product p3 = entityFactory.getNewProduct("PROD-03-TEST");

		p2 = productJpaRepository.insert(p2);
		p3 = productJpaRepository.insert(p3);

		List<Product> products = productJpaRepository.selectAll();

		assertNotNull(products);
		assertEquals(products.size(), 3);
		assertEquals(products.get(0).getCode(), productDB.getCode());
		assertEquals(products.get(1).getCode(), p2.getCode());
		assertEquals(products.get(2).getCode(), p3.getCode());

		productJpaRepository.delete(p3);

		products = productJpaRepository.selectAll();
		assertNotNull(products);
		assertEquals(products.size(), 2);
	}

	@Test
	public void updatedProductTest() {
		productDB.setDescription("Description updated");
		productDB.setName("Name updated");
		productDB.setPrice(10.0);
		productDB.setQuantity(10);
		
		Product prodUpdated = productJpaRepository.update(productDB);
		
		assertNotNull(prodUpdated);
		assertEquals(prodUpdated, product);
		assertEquals(prodUpdated.getId(), productDB.getId());
		assertEquals(prodUpdated.getCode(), productDB.getCode());
		Double amountProdUpdated = prodUpdated.getAmountStock();
		Double amountProdDB = productDB.getPrice()*prodUpdated.getQuantity();
		assertEquals(amountProdUpdated, amountProdDB);
	}

	@Test
	public void deleteProductTest() {
		Boolean result = productJpaRepository.delete(product);
		assertTrue(result);
	}

	private void clearDB() {
		Collection<Product> list = productJpaRepository.selectAll();
		list.forEach(prod -> {
			try {
				productJpaRepository.delete(prod);
			} catch (RepositoryException e) {
				e.printStackTrace();
				throw new RepositoryException(e.getMessage());
			}
		});
	}

}
