package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.com.unigranrio.transporte.modelo.Usuario;

@Repository
@SuppressWarnings("serial")
public class UsuarioDao implements Serializable {

	@PersistenceContext
	EntityManager em;

	private DAO<Usuario> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Usuario>(this.em, Usuario.class);
	}

	public void adiciona(Usuario t) {
		dao.adiciona(t);
	}

	public void remove(Usuario t) {
		dao.remove(t);
	}

	public void atualiza(Usuario t) {
		dao.atualiza(t);
	}

	public List<Usuario> listaTodos() {
		return dao.listaTodos();
	}

	public Usuario buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	public Usuario existe(String email, String senha) {

		TypedQuery<Usuario> query = em.createQuery(
				" select u from Usuario u "
						+ " where u.email = :pEmail and u.senha = :pSenha",
				Usuario.class);

		query.setParameter("pEmail", email);
		query.setParameter("pSenha", senha);
		
			Usuario resultado;
			try {
				resultado = query.getSingleResult();
				
			} catch (NoResultException e) {
				return null;
			}
			
			return resultado;
	}

}
