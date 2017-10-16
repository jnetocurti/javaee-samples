package br.com.jnetocurti.jpa.repository;

import java.io.Serializable;
import java.util.Collection;

public interface Repository<T, K extends Serializable> {

	T save(T entity);

	T update(T entity);

	void delete(T entity);

	void deleteById(@SuppressWarnings("unchecked") K... ids);

	Collection<T> findAll();

	Collection<T> findAll(int firstResult, int maxResults);

	T findById(K id);

}
