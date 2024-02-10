package domain.factory;

import java.time.Instant;
import java.util.Random;

import domain.Address;
import domain.Client;
import domain.Product;
import domain.Sale;
import domain.Sale.Status;

public class EntityFactory {
	
	private Random rd = new Random();
	
	public Address getNewAddress() {
		return new Address("Street",  rd.nextInt(0, 100), "City", "State", rd.nextLong(0, 99999));
	};
	
	public Client getNewClient(Long cpf) {
		return new Client(cpf, "Jhon Doe", rd.nextLong(0, 9999999), getNewAddress(), Instant.now());
	};
	
	public Product getNewProduct(String code) {
		return new Product(code, "Product Name", "Product Description", rd.nextDouble(0, 99999), rd.nextInt(0, 999));
	};
	
	public Sale getNewSale(String code) {
		Client c = getNewClient(rd.nextLong(0, 999999999));
		Product p = getNewProduct("CODE-PROD-01");
		Sale sell =  new Sale(code, c, Instant.now(), Status.INICIADA);
		sell.adicionarProduto(p, p.getQuantity());
		return sell;
	};

}
