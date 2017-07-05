package br.com.unigranrio.transporte.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import br.com.unigranrio.transporte.modelo.Pedido;
import br.com.unigranrio.transporte.modelo.StatusEnum;
import br.com.unigranrio.transporte.modelo.Usuario;

@Repository
@SuppressWarnings("serial")
public class PedidoDao implements Serializable {

	@PersistenceContext
	EntityManager manager;

	private DAO<Pedido> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Pedido>(this.manager, Pedido.class);
	}

	public void adiciona(Pedido t) {
		dao.adiciona(t);
	}

	public void remove(Pedido t) {
		dao.remove(t);
	}

	public void atualiza(Pedido t) {
		dao.atualiza(t);
	}

	public List<Pedido> listaTodos() {
		return dao.listaTodos();
	}

	public Pedido buscaPorId(Integer id) {
		return dao.buscaPorId(id);
	}

	public List<Pedido> pedidosPorUsuarioStatus(Usuario usuario, StatusEnum status) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		Root<Pedido> root = query.from(Pedido.class);

		Predicate conjunction = builder.conjunction();

		Path<Integer> usuarioPath = root.<Usuario>get("usuario").<Integer>get("id");
		Path<String> statusPath = root.<String>get("status");

		if (usuario != null) {
			Predicate nomeIgual = builder.equal(usuarioPath, usuario.getId());
			conjunction = builder.and(nomeIgual);
		}

		Predicate statusIgual = builder.equal(statusPath, status);

		conjunction = builder.and(conjunction, statusIgual);

		query.where(conjunction);

		TypedQuery<Pedido> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();

	}

	public List<Pedido> pedidosAutorizadosPendentes(Usuario usuario) {

		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		Root<Pedido> root = query.from(Pedido.class);

		Predicate conjunction = builder.conjunction();
		Predicate conjunction1 = builder.conjunction();

		Path<Integer> usuarioPath = root.<Usuario>get("usuario").<Integer>get("id");
		Path<String> statusPath = root.<String>get("status");

		if (usuario != null) {
			Predicate nomeIgual = builder.equal(usuarioPath, usuario.getId());
			conjunction = builder.and(nomeIgual);
		}

		Predicate status1 = builder.equal(statusPath, StatusEnum.AUTORIZADO);
		Predicate status2 = builder.equal(statusPath, StatusEnum.PEDIDO_ENVIADO);

		conjunction1 = builder.or(status1, status2);

		conjunction = builder.and(conjunction, conjunction1);

		query.where(conjunction);

		TypedQuery<Pedido> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();

		/*
		 * Criteria Api do Hibernete
		 * 
		 * Session session = manager.unwrap(Session.class); Criteria criteria =
		 * session.createCriteria(Pedido.class);
		 * 
		 * if (usuario != null) { criteria.add(Restrictions.eq("usuario.id",
		 * usuario.getId())); }
		 * 
		 * Criterion r1 = Restrictions.eq("usuario.status",
		 * StatusEnum.AUTORIZADO);
		 * 
		 * Criterion r2 = Restrictions.eq("usuario.status",
		 * StatusEnum.PEDIDO_ENVIADO);
		 * 
		 * criteria.add(Restrictions.or(r1, r2));
		 * 
		 * return (List<Pedido>) criteria.list();
		 */

	}

	public List<Pedido> pedidosPorStatus(StatusEnum status) {
		TypedQuery<Pedido> query = manager.createQuery("select p from Pedido p where p.status= :pStatus", Pedido.class);

		query.setParameter("pStatus", status);

		List<Pedido> pedidos = null;
		try {
			pedidos = query.getResultList();

		} catch (Exception e) {

			System.out.println("não achou Pedidos");
			e.printStackTrace();
		}

		return pedidos;
	}

	public List<Pedido> pedidosTodos(Usuario usuario) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Pedido> query = builder.createQuery(Pedido.class);
		Root<Pedido> root = query.from(Pedido.class);

		Path<Integer> usuarioPath = root.<Usuario>get("usuario").<Integer>get("id");

		Predicate nomeIgual = null;

		if (usuario != null) {
			nomeIgual = builder.equal(usuarioPath, usuario.getId());
			query.where(nomeIgual);
		}

		TypedQuery<Pedido> typedQuery = manager.createQuery(query);
		return typedQuery.getResultList();

		/*
		 * Criteria Api do Hibernete Session session =
		 * manager.unwrap(Session.class); Criteria criteria =
		 * session.createCriteria(Pedido.class);
		 * 
		 * if (usuario != null) { criteria.add(Restrictions.like("usuario.id",
		 * usuario.getId())); }
		 * 
		 * return (List<Pedido>) criteria.list();
		 */

	}

	public Pedido buscaPedidoComDestinos(Integer numeroPedido) {
		TypedQuery<Pedido> query = manager.createQuery(
				"select p from Pedido p join fetch p.destinos where p.numeroPedido = :pNumeroPedido", Pedido.class);
		query.setParameter("pNumeroPedido", numeroPedido);

		return query.getSingleResult();
	}

	//
	@SuppressWarnings("unchecked")
	public List<Integer> anoDosPedidoPorStatus(StatusEnum status) {

		Query query = manager.createNativeQuery(
						"SELECT Distinct EXTRACT(YEAR FROM p.dataPedido) FROM Pedido p where p.status = :pStatus ");
		System.out.println(status.name());
		query.setParameter("pStatus", status.toString());
		
		return query.getResultList();

	}

	@SuppressWarnings("unchecked")
	public List<Pedido> listaPorDataAno(String year, StatusEnum status) {
		System.out.println(year);

		Query query = manager.createNativeQuery(
				"SELECT * FROM Pedido p where EXTRACT(YEAR FROM p.dataPedido) = :pDataPedido and p.status = :pStatus");

		query.setParameter("pDataPedido", year);
		query.setParameter("pStatus", status.toString());
		return query.getResultList();
	}

}