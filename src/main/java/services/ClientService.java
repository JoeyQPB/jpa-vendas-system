package services;

import domain.Client;
import repository.IClientJpaRepository;

public class ClientService extends GenericService<Client, Long> implements IClientService {

	public ClientService(IClientJpaRepository repository) {
		super(repository);
	}

}
