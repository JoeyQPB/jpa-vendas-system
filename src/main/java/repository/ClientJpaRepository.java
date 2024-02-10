package repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import config.ConnectionJPA;
import domain.Client;
import repository.generic.JPAGenericRepository;

public class ClientJpaRepository extends JPAGenericRepository<Client, Long> implements IClientJpaRepository {
	public ClientJpaRepository() {
		super(Client.class);
	}
	
	@Override
	public Client selectByCpf(Long cpf) {
		EntityManager entityManager = ConnectionJPA.getEntityManager();
		entityManager.getTransaction().begin();
        TypedQuery<Client> query = entityManager.createQuery(
                "SELECT c FROM Client c WHERE c.cpf = :cpf", Client.class);
        query.setParameter("cpf", cpf);
        Client entity = query.getSingleResult();
		entityManager.getTransaction().commit();
		return entity;
	}
}
