package repositoryTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import domain.Address;
import domain.Client;
import domain.factory.EntityFactory;
import exceptions.RepositoryException;
import repository.ClientJpaRepository;
import repository.IClientJpaRepository;

public class ClientRepositoryTest {

	private IClientJpaRepository clienteJpaRepository;
	private EntityFactory entityFactory;
	private Client client;
	private Client clientDB;

	public ClientRepositoryTest() {
		this.clienteJpaRepository = new ClientJpaRepository();
		this.entityFactory = new EntityFactory();
	}

	@Before
	public void init() {
		clearDB();
		this.client = entityFactory.getNewClient(90909090L);
		this.clientDB = clienteJpaRepository.insert(client);
	}

	@After
	public void end() {
		clearDB();
	}

	@Test
	public void insertClientTest() {
		assertNotNull(clientDB);
		assertNotNull(clientDB.getId());
		assertEquals(clientDB, client);
		assertEquals(clientDB.getAdress(), client.getAdress());
		assertEquals(clientDB.getCelNumber(), client.getCelNumber());
		assertEquals(clientDB.getCpf(), client.getCpf());
		assertEquals(clientDB.getName(), client.getName());
	}

	@Test
	public void selectClientTest() {
		Client clientTestSelect = clienteJpaRepository.select(client.getId());
		assertNotNull(clientTestSelect);
		assertNotNull(clientTestSelect.getId());
		assertEquals(clientTestSelect, client);
		assertEquals(clientTestSelect.getAdress(), client.getAdress());
		assertEquals(clientTestSelect.getCelNumber(), client.getCelNumber());
		assertEquals(clientTestSelect.getCpf(), client.getCpf());
		assertEquals(clientTestSelect.getName(), client.getName());
	}
	
	@Test
	public void selectClientByCpfTest() {
		Client clientTestSelect = clienteJpaRepository.selectByUniqueValue(client.getCpf());
		assertNotNull(clientTestSelect);
		assertNotNull(clientTestSelect.getId());
		assertEquals(clientTestSelect, client);
		assertEquals(clientTestSelect.getAdress(), client.getAdress());
		assertEquals(clientTestSelect.getCelNumber(), client.getCelNumber());
		assertEquals(clientTestSelect.getCpf(), client.getCpf());
		assertEquals(clientTestSelect.getName(), client.getName());
	}

	@Test
	public void selectAllClientTest() {
		 Client c2 = entityFactory.getNewClient(80808080L);
		 Client c3 = entityFactory.getNewClient(70707070L);
		 
		 c2 = clienteJpaRepository.insert(c2);
		 c3 = clienteJpaRepository.insert(c3);
		 
		 List<Client> clients = clienteJpaRepository.selectAll();
		 
		 assertNotNull(clients);
		 assertEquals(clients.size(), 3);
		 assertEquals(clients.get(0).getCpf(), clientDB.getCpf());
		 assertEquals(clients.get(1).getCpf(), c2.getCpf());
		 assertEquals(clients.get(2).getCpf(), c3.getCpf());
		 
		 clienteJpaRepository.delete(c3);
		 
		 clients = clienteJpaRepository.selectAll();
		 assertNotNull(clients);
		 assertEquals(clients.size(), 2);
	}
	
	@Test
	public void updateClientTest() {
		Address address = new Address("Street updated", 10, "City updated", "State updated", 450000L);
		clientDB.setCelNumber(123456789L);
		clientDB.setCpf(123456789L);
		clientDB.setName("Name updated");
		clientDB.setAdress(address);

		Client clientUpdated = clienteJpaRepository.update(clientDB);
		
		assertNotNull(clientUpdated);
		assertNotNull(clientUpdated.getId());
		assertEquals(clientUpdated, client);
		assertEquals(clientUpdated.getAdress(), client.getAdress());
		assertEquals(clientUpdated.getCelNumber(), client.getCelNumber());
		assertEquals(clientUpdated.getCpf(), client.getCpf());
		assertEquals(clientUpdated.getName(), client.getName());
	}

	@Test
	public void deleteClientTest() {
		Boolean result = clienteJpaRepository.delete(clientDB);
		assertTrue(result);
	}

	private void clearDB() {
		Collection<Client> list = clienteJpaRepository.selectAll();
		list.forEach(c -> {
			try {
				clienteJpaRepository.delete(c);
			} catch (RepositoryException e) {
				e.printStackTrace();
				throw new RepositoryException(e.getMessage());
			}
		});
	}

}
