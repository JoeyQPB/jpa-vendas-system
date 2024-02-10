package services;

import java.io.Serializable;
import java.util.Collection;

import domain.interfaces.IPersistence;
import exceptions.RepositoryException;

public interface IGenericService <T extends IPersistence, E extends Serializable> {
	/**
     * Método para cadastrar novos registro no banco de dados
     *
     * @param entity a ser cadastrado
     * @return retorna verdadeiro para cadastrado e falso para não cadastrado
	 * @throws DAOException 
     */
    public T create(T entity) throws RepositoryException;

    /**
     * Método para excluir um registro do banco de dados
     *
     * @param valor chave única do dado a ser excluído
     * @throws DAOException 
     */
    public Boolean delete(E value) throws RepositoryException;

    /**
     *Método para alterar um registro no bando de dados.
     *
     * @param entity a ser atualizado
     * @throws DAOException 
     */
    public T put(T entity) throws RepositoryException;

    /**
     * Método para consultar um registro no banco de dados
     *
     * @param valor chave única do dado a ser consultado
     * @return
     * @throws DAOException 
     */
    public T get(E value) throws RepositoryException;

    /**
     * Método que irá retornar todos os registros do banco de dados de uma determinado dado ou tabela
     *
     * @return Registros encontrados
     * @throws DAOException 
     */
    public Collection<T> getAll() throws RepositoryException;
}
