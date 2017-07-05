package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintDeclarationException;

import org.springframework.stereotype.Repository;

import com.mchange.util.DuplicateElementException;

import br.com.unigranrio.transporte.modelo.Motorista;
import br.com.unigranrio.transporte.modelo.Pedido;
import br.com.unigranrio.transporte.util.JSFUtil;

@Repository
@SuppressWarnings("serial")
public class MotoristaDao implements Serializable {

	@PersistenceContext
	EntityManager em;

	private DAO<Motorista> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Motorista>(this.em, Motorista.class);
	}

	public void adiciona(Motorista t) {
		dao.adiciona(t);
	}

	public void remove(Motorista t) {
		
			dao.remove(t);
		
	}

	public void atualiza(Motorista t) {
		dao.atualiza(t);
	}

	public List<Motorista> listaTodos() {
		return dao.listaTodos();
	}

	public Motorista buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}
	
	/*public Boolean verificaRelacionamentos(Integer id){
		
		Query query = em.createNativeQuery("select distinct m.nome from Motorista m, Pedido p, Pedido_Motorista pm where m.id = 1 and m.id = pm.motoristas_id and p.numeroPedido = pm.Pedido_numeroPedido", Pedido.class);
		query.setParameter("mId", id);
		
		if(query.getSingleResult() == null){
			return false;
		}
		return true;
		
	}*/
	

}
