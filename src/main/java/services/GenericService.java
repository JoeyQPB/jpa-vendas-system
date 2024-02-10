package services;

import java.io.Serializable;
import java.util.Collection;

import domain.interfaces.IPersistence;
import exceptions.RepositoryException;
import repository.generic.IJPAGenericRepository;

public abstract class GenericService<T extends IPersistence, E extends Serializable> implements IGenericService<T, E> {

	protected IJPAGenericRepository<T,E> repository;
	
	public GenericService(IJPAGenericRepository<T,E> repository) {
		this.repository = repository;
	}
	
	@Override
	public T create(T entity) throws RepositoryException {
		return repository.insert(entity);
	}
	
	@Override
	public T get(E value) throws RepositoryException {
		return repository.select(value);
	}

	@Override
	public Collection<T> getAll() throws RepositoryException {
		return repository.selectAll();
	}
	
	@Override
	public T put(T entity) throws RepositoryException {
		return repository.update(entity);
		
	}
	
	@Override
	public Boolean delete(E value) throws RepositoryException {
		T entity = this.get(value);
		return repository.delete(entity);
	}

}
