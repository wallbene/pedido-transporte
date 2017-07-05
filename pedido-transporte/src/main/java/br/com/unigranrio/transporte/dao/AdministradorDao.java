package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.unigranrio.transporte.modelo.Administrador;

@Repository
@SuppressWarnings("serial")
public class AdministradorDao implements Serializable {

	@PersistenceContext
	EntityManager em;

	private DAO<Administrador> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Administrador>(this.em, Administrador.class);
	}

	public void adiciona(Administrador t) {
		dao.adiciona(t);
	}

	public void remove(Administrador t) {
		dao.remove(t);
	}

	public void atualiza(Administrador t) {
		dao.atualiza(t);
	}

	public List<Administrador> listaTodos() {
		return dao.listaTodos();
	}

	public Administrador buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	public Administrador existe(String email, String senha) {

		TypedQuery<Administrador> query = em.createQuery(
				" select u from Administrador u "
						+ " where u.email = :pEmail and u.senha = :pSenha",
				Administrador.class);

		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		
		Administrador resultado;
		try {
			resultado = query.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
		
		return resultado;
	}

}
