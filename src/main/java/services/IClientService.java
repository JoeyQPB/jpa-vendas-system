package services;

import domain.Client;

public interface IClientService extends IGenericService<Client, Long> {

	public Client getByCpf(Long cpf);
}
