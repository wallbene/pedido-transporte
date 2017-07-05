package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.unigranrio.transporte.modelo.Veiculo;

@Repository
@SuppressWarnings("serial")
public class VeiculoDao implements Serializable {

	@PersistenceContext
	EntityManager manager;

	private DAO<Veiculo> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Veiculo>(this.manager, Veiculo.class);
	}

	public void adiciona(Veiculo t) {
		dao.adiciona(t);
	}

	public void remove(Veiculo t) {
		dao.remove(t);
	}

	public void atualiza(Veiculo t) {
		dao.atualiza(t);
	}

	public List<Veiculo> listaTodos() {
		return dao.listaTodos();
	}

	public Veiculo buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	public List<Veiculo> listaDisponiveis(){
		return manager.createQuery("select v from Veiculo v where v.situacao = true", Veiculo.class).getResultList();
		
	}

}
