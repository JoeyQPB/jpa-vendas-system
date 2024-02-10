package services;

import domain.Client;
import repository.IClientJpaRepository;

public class ClientService extends GenericService<Client, Long> implements IClientService {

	private IClientJpaRepository repository;
	
	public ClientService(IClientJpaRepository repository) {
		super(repository);
		this.repository = repository;
	}

	@Override
	public Client getByCpf(Long cpf) {
		return repository.selectByCpf(cpf);
	}

}
