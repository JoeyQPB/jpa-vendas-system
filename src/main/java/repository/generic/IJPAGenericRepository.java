package repository.generic;

import java.io.Serializable;
import java.util.List;

import domain.interfaces.IPersistence;

public interface IJPAGenericRepository<T extends IPersistence, E extends Serializable> {
	
	public T insert(T entity);
	public T select(Long value);
	public T selectByUniqueValue(E value);
	public List<T> selectAll();
	public T update(T entity);
	public Boolean delete(T entity);

}
