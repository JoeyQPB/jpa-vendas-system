package servicesTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Client;
import domain.factory.EntityFactory;
import repository.ClientJpaRepository;
import repository.IClientJpaRepository;
import services.ClientService;
import services.IClientService;

public class ClientServiceTest {
	
	EntityFactory entityFactory = new EntityFactory();
	private IClientJpaRepository repository;
	private IClientService clientService;
	private Client client;
	
	public ClientServiceTest() {
		repository = new ClientJpaRepository();
		clientService = new ClientService(repository);
	}
	
	@Before
	void init() {
		client = entityFactory.getNewClient(10101010L);
	}
	
	@After
	void end() {
		clientService.delete(20202020L);
		clientService.delete(30303030L);
	}
	
	@Test
	public void createClientTest() {
		Client clientDB = clientService.create(client);
		assertNotNull(clientDB);
		assertNotNull(clientDB.getId());
		assertEquals(clientDB, client);
	}
	
	@Test
	public void getClientTest() {
		Client clientDB = clientService.get(client.getId());
		assertNotNull(clientDB);
		assertNotNull(clientDB.getId());
		assertEquals(clientDB, client);
	}
	
	@Test
	public void getByUniqueValue() {
		Client clientDB = clientService.getByUniqueValue(client.getCpf());
		assertNotNull(clientDB);
		assertNotNull(clientDB.getId());
		assertEquals(clientDB, client);
	}
	
	
	@Test
	public void getAllClientTest() {
		repository.insert(entityFactory.getNewClient(20202020L));
		repository.insert(entityFactory.getNewClient(30303030L));
		Collection<Client> clientList = clientService.getAll();
		assertNotNull(clientList);
		assertEquals(clientList.size(), 3);
		
	}
	
	@Test
	public void updateClientTest() {
		Long newNumber = 11111111L;
		String newName = "New name";
		client.setCelNumber(newNumber);
		client.setName(newName);
		Client clientDB = clientService.put(client);
		assertEquals(clientDB, client);
		assertEquals(clientDB.getCelNumber(), newNumber);
		assertEquals(clientDB.getName(), newName);
	}
	
	@Test
	public void deleteClientTest() {
		Boolean result = clientService.delete(client.getCpf());
		assertTrue(result);	
	}

}
