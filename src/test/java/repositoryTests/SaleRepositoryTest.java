package repositoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Client;
import domain.Product;
import domain.Sale;
import domain.Sale.Status;
import domain.factory.EntityFactory;
import exceptions.RepositoryException;
import repository.ClientJpaRepository;
import repository.IClientJpaRepository;
import repository.IProductJpaRepository;
import repository.ISaleJpaRepository;
import repository.ProductJpaRepository;
import repository.SaleJpaRepository;

public class SaleRepositoryTest {
	
	private EntityFactory entityFactory;
	private ISaleJpaRepository saleJpaRepository;
	private IClientJpaRepository clientJpaRepository;
	private IProductJpaRepository productJpaRepository;
	private Client client = null;
	private Product product = null;

	public SaleRepositoryTest() {
		this.entityFactory = new EntityFactory();
		this.saleJpaRepository = new SaleJpaRepository();
		this.clientJpaRepository = new ClientJpaRepository();
		this.productJpaRepository = new ProductJpaRepository();
	}
	
	@Before
	public void init(){
		end();
		this.client = createClient(50505050L);
		this.product = createProduct("PROD-01-SALE_TEST");
	}
	
	@After
	public void end() {
		deleteAllSales();
		deleteAllProducts();
		if (client != null) clientJpaRepository.delete(client);
	}
	
	@Test
	public void selectSaleTest() {
		Sale saleBD = createSale("SALE-01-TEST");		
		Sale saleSearched = saleJpaRepository.select(saleBD.getCode());
		assertNotNull(saleSearched);
		assertEquals(saleSearched.getCode(), saleBD.getCode());
	}

	@Test
	public void insertSaleTest() {
		Sale saleBD = createSale("SALE-01-TEST");
		assertNotNull(saleBD);
		assertNotNull(saleBD.getId());
	}

	@Test
	public void cancelarSale() {
		String saleCode = "SALE-01-TEST";
		Sale sale = createSale(saleCode);

		saleJpaRepository.cancelSale(sale);

		Sale saleSelected = saleJpaRepository.select(saleCode);
		assertEquals(saleCode, saleSelected.getCode());
		assertEquals(Status.CANCELADA, saleSelected.getStatus());
	}

	@Test
	public void adicionarMaisproductsDoMesmo() {
		String saleCode = "SALE-01-TEST";
		Sale sale = createSale(saleCode);

		Sale saleSelected = saleJpaRepository.select(saleCode);
		assertEquals(sale, saleSelected);
		saleSelected.adicionarProduto(product, 1);
		saleSelected.adicionarProduto(product, 1);

		assertTrue(saleSelected.getQuantidadeTotalproducts() == 3);
		Double valorTotal = product.getPrice()*3;
		assertTrue(saleSelected.getTotal().equals(valorTotal));
		assertTrue(saleSelected.getStatus().equals(Status.INICIADA));
	}

	@Test
	public void adicionarMaisproductsDiferentes() {
		String saleCode = "SALE-01-TEST";
		createSale(saleCode);

		Product prod = createProduct("PROD-01-TEST_SALE");
		assertNotNull(prod);

		Sale SaleConsultada = saleJpaRepository.select(saleCode);
		SaleConsultada.adicionarProduto(prod, 1);

		assertTrue(SaleConsultada.getQuantidadeTotalproducts() == 2);
		assertTrue(SaleConsultada.getStatus().equals(Status.INICIADA));
	}

	@Test(expected = RepositoryException.class)
	public void salvarSaleMesmoCodigoExistente() {
		Sale Sale = createSale("SALE-02-TEST");
		saleJpaRepository.insert(Sale);

		Optional<Sale> saleNull = Optional.ofNullable(saleJpaRepository.insert(Sale));
		assertNull(saleNull);
		assertTrue(Sale.getStatus().equals(Status.INICIADA));
	}

	@Test
	public void removerproduct(){
		String saleCode = "SALE-01-TEST";
		createSale(saleCode);

		Product prod = createProduct("PROD-01-TEST_SALE");
		assertNotNull(prod);

		Sale SaleConsultada = saleJpaRepository.select(saleCode);
		SaleConsultada.adicionarProduto(prod, 1);
		
		
		assertTrue(SaleConsultada.getQuantidadeTotalproducts() == 2);

		SaleConsultada.removerProduto(prod, 1);
		assertTrue(SaleConsultada.getQuantidadeTotalproducts() == 1);
		assertTrue(SaleConsultada.getStatus().equals(Status.INICIADA));
	}

	@Test
	public void removerTodosproducts() {
		String saleCode = "SALE-01-TEST";
		createSale(saleCode);

		Product prod = createProduct("PROD-01-TEST_SALE");
		assertNotNull(prod);


		Sale SaleConsultada = saleJpaRepository.select(saleCode);
		SaleConsultada.adicionarProduto(prod, 1);
		
		
		assertTrue(SaleConsultada.getQuantidadeTotalproducts() == 2);

		SaleConsultada.removerTodosproducts();
		assertTrue(SaleConsultada.getQuantidadeTotalproducts() == 0);
		assertTrue(SaleConsultada.getTotal().equals(0.0));
		assertTrue(SaleConsultada.getStatus().equals(Status.INICIADA));
	}

	@Test
	public void finalizarSale() {
		String saleCode = "SALE-01-TEST";
		Sale sale = createSale(saleCode);
		
		saleJpaRepository.finalizeSale(sale);

		Sale SaleConsultada = saleJpaRepository.select(saleCode);
		assertEquals(sale.getCode(), SaleConsultada.getCode());
		assertEquals(Status.CONCLUIDA, SaleConsultada.getStatus());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void tentarAdicionarproductsSaleFinalizada() {
		String saleCode = "SALE-01-TEST";
		Sale sale = createSale(saleCode);

		saleJpaRepository.finalizeSale(sale);
		Sale SaleConsultada = saleJpaRepository.select(saleCode);
		assertEquals(sale.getCode(), SaleConsultada.getCode());
		assertEquals(Status.CONCLUIDA, SaleConsultada.getStatus());

		SaleConsultada.adicionarProduto(this.product, 1);
	}
	
	private Product createProduct(String code) {
		Product prod = entityFactory.getNewProduct(code);
		prod = productJpaRepository.insert(prod);
		return prod;
	}
	
	private Client createClient(Long cpf) {
		Client client = entityFactory.getNewClient(cpf); 
		client = clientJpaRepository.insert(client);
		return client;
	}
	
	private Sale createSale(String code) {
		Sale sale = entityFactory.getNewSale(code);
		sale = saleJpaRepository.insert(sale);
		return sale;
	}
	
	private void deleteAllProducts() {
		Collection<Product> list = productJpaRepository.selectAll();
		list.forEach(prod -> {
			try {
				productJpaRepository.delete(prod);
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		});
	}

	private void deleteAllSales() {
		saleJpaRepository.cleanDB();
	}
}
