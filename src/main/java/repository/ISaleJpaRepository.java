package repository;

import domain.Sale;
import repository.generic.IJPAGenericRepository;

public interface ISaleJpaRepository extends IJPAGenericRepository<Sale, String> {

	public void finalizeSale(Sale sale);
	
	public void cancelSale(Sale sale);
	
	/**
	 * Usando este método para evitar a exception org.hibernate.LazyInitializationException
	 * Ele busca todos os dados de objetos que tenham colletion pois a mesma por default é lazy
	 * Mas você pode configurar a propriedade da collection como fetch = FetchType.EAGER na anotação @OneToMany e usar o consultar genérico normal
	 * 
	 * OBS: Não é uma boa prática utiliar FetchType.EAGER pois ele sempre irá trazer todos os objetos da collection
	 * mesmo sem precisar utilizar.
	 */
	public Sale selectWithCollection(String code);
	
	public void cleanDB();
}
