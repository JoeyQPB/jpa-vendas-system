package repository;

import domain.Client;
import repository.generic.JPAGenericRepository;

public class ClientJpaRepository extends JPAGenericRepository<Client, Long> implements IClientJpaRepository {
	public ClientJpaRepository() {
		super(Client.class);
	}
}
