package repository;

import domain.Client;
import repository.generic.IJPAGenericRepository;

public interface IClientJpaRepository extends IJPAGenericRepository<Client, Long> {

	public Client selectByCpf(Long cpf);
}
