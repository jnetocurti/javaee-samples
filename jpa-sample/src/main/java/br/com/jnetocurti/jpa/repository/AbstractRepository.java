package br.com.jnetocurti.jpa.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class AbstractRepository<T, K extends Serializable> implements
		Repository<T, K> {

	@PersistenceContext
	private EntityManager em;

	private Class<T> clazz;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractRepository() {

		this.clazz = (Class) ((ParameterizedType) this.getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public T save(T entity) {
		this.em.persist(entity);
		return entity;
	}

	@Override
	public T update(T entity) {
		return this.em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		this.em.remove(entity);
	}

	@Override
	public void deleteById(@SuppressWarnings("unchecked") K... ids) {

		Query query = this.em.createQuery("DELETE FROM "
				+ this.clazz.getCanonicalName() + " WHERE id in :ids");
		
		query.setParameter("ids", Arrays.asList(ids));
		
		query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findAll() {

		return this.em.createQuery("FROM " + this.clazz.getCanonicalName())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<T> findAll(int firstResult, int maxResults) {

		Query query = this.em.createQuery("FROM "
				+ this.clazz.getCanonicalName());

		query.setFirstResult(firstResult);
		query.setMaxResults(maxResults);

		return query.getResultList();
	}

	@Override
	public T findById(K id) {
		return this.em.find(this.clazz, id);
	}

}
