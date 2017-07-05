package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.ConstraintViolationException;

import com.mchange.util.DuplicateElementException;

import br.com.unigranrio.transporte.util.JSFUtil;

@SuppressWarnings("serial")
public class DAO<T> implements Serializable {

	private final Class<T> classe;
	
	private EntityManager em;

	public DAO(EntityManager manager, Class<T> classe) {
		this.em = manager;
		this.classe = classe;
	}

	public void adiciona(T t) {
		// persiste o objeto
		em.persist(t);
	}

	public void remove(T t) {
		
		try {
			System.out.println("exeção no dao");
			em.remove(t);
			
		} catch (Exception e) {
			JSFUtil.retornarMensagemInfo("Erro","Motorista não pode ser Exluído!" , "messages");
			System.out.println(e);
		}
			
			
	}

	public void atualiza(T t) {
			em.merge(t);
		
	}

	public List<T> listaTodos() {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).getResultList();

		return lista;
	}

	public T buscaPorId(Integer id) {
		T instancia = em.find(classe, id);
		return instancia;
	}

	public int contaTodos() {
		long result = (Long) em.createQuery("select count(n) from livro n")
				.getSingleResult();

		return (int) result;
	}

	public List<T> listaTodosPaginada(int firstResult, int maxResults) {
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
		query.select(query.from(classe));

		List<T> lista = em.createQuery(query).setFirstResult(firstResult)
				.setMaxResults(maxResults).getResultList();

		return lista;
	}

}
