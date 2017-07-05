package br.com.unigranrio.transporte.bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.unigranrio.transporte.dao.MotoristaDao;
import br.com.unigranrio.transporte.dao.PedidoDao;
import br.com.unigranrio.transporte.dao.VeiculoDao;
import br.com.unigranrio.transporte.modelo.Destino;
import br.com.unigranrio.transporte.modelo.EstadosEnum;
import br.com.unigranrio.transporte.modelo.Motorista;
import br.com.unigranrio.transporte.modelo.Pedido;
import br.com.unigranrio.transporte.modelo.StatusEnum;
import br.com.unigranrio.transporte.modelo.TipoTransporteEnum;
import br.com.unigranrio.transporte.modelo.Usuario;
import br.com.unigranrio.transporte.modelo.Veiculo;
import br.com.unigranrio.transporte.util.JSFUtil;

@Controller
public class PedidoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = new Pedido();
	private Destino destino = new Destino();

	private Integer veiculoId;
	private Integer motoristaId;

	private List<Pedido> pedidos;

	@Inject
	PedidoDao pedidoDao;

	@Inject
	VeiculoDao veiculoDao;

	@Inject
	MotoristaDao motoristaDao;
	

	/*Método que pega os pedidos e gera o gráfico*/ 
/*	public BarChartModel getVendasModel() {

		BarChartModel model = new BarChartModel();
		model.setAnimate(true);

		ChartSeries vendaSerie = new ChartSeries();
		vendaSerie.setLabel("Vendas 2016");

		List<Venda> vendas = getVendas();
		for (Venda venda : vendas) {
			vendaSerie.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}

		model.addSeries(vendaSerie);

		return model;
	}*/
	
	

	// Se for um novo pedido retorna a lista de destinos na memória
	// se não returna os destinos dos pedidos que estão no banco
	// isso vai evitar lançar a LazyInitializationException e assim não
	// precisamos mudar o comportamento padão de lazy para Eager
	public List<Destino> getDestinosDoPedido() {
		
		return this.getPedido().getDestinos();
		

	}

	public List<Veiculo> getVeiculosDoPedido() {

		return this.getPedido().getVeiculos();
	}

	public List<Motorista> getMotoristasDoPedido() {

		return this.getPedido().getMotoristas();
	}

	public List<Veiculo> getVeiculosDisponiveis() {
		return veiculoDao.listaDisponiveis();
	}

	public List<Motorista> getMotoristas() {
		return motoristaDao.listaTodos();
	}

	public List<Pedido> getPedidos() {
		if (this.pedidos == null) {
			this.pedidos = pedidoDao.listaTodos();
		}
		return this.pedidos;
	}

	public void carregarPedidoPelaId() {
		this.setPedido(pedidoDao.buscaPorId(this.getPedido().getNumeroPedido()));
	}

	public void adicionarDestino() {
		System.out.println(this.getPedido().getDestinos().size());
		System.out.println("Adicionando: " + this.destino.getLogradouro());
		this.getPedido().adicionaDestino(this.destino);
		System.out.println(this.getPedido().getDestinos().size());
		this.setDestino(new Destino());
	}

	public void removerDestino(Destino destino) {

		System.out.println(this.getPedido().getDestinos().size());
		System.out.println("Removendo: " + destino.getLogradouro());
		this.getPedido().removeDestino(destino);
		System.out.println(this.getPedido().getDestinos().size());
	}

	public void adicionarVeiculo() {
		System.out.println("Adicionando: Veiculo: " + this.veiculoId);
		Veiculo veiculo = veiculoDao.buscaPorId(this.veiculoId);
		this.getPedido().adicionaVeiculo(veiculo);
	}

	public void removerVeiculo(Veiculo veiculo) {
		this.getPedido().removeVeiculo(veiculo);
	}

	public void adicionarMotorista() {
		System.out.println("Adicionando: Motorista");
		this.getPedido().adicionaMotorista(motoristaDao.buscaPorId(this.motoristaId));
	}

	public void removerMotorista(Motorista motorista) {
		this.getPedido().removeMotorista(motorista);
	}

	public void carregarPedidosAutorizadosPendentes() {

		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = this.pedidoDao.pedidosAutorizadosPendentes(usuarioAtual);

	}

	public void carregarTodosPedidos() {
		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = this.pedidoDao.pedidosTodos(usuarioAtual);

	}

	public void carregarPedidosAutorizados() {

		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = pedidoDao.pedidosPorUsuarioStatus(usuarioAtual, StatusEnum.AUTORIZADO);

	}

	public void carregarPedidosAtendidos() {

		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = pedidoDao.pedidosPorUsuarioStatus(usuarioAtual, StatusEnum.ATENDIDO);

	}

	public void carregarPedidosCancelados() {

		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = pedidoDao.pedidosPorUsuarioStatus(usuarioAtual, StatusEnum.CANCELADO);

	}

	public void carregarPedidosNegados() {

		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");
		this.pedidos = pedidoDao.pedidosPorUsuarioStatus(usuarioAtual, StatusEnum.NEGADO);

	}

	public void carregarPedidosAutorizacaoPendentes() {

		this.pedidos = pedidoDao.pedidosPorStatus(StatusEnum.PEDIDO_ENVIADO);

	}

	@Transactional
	public void efetuarPedido() {
		/*System.out.println("Gravando pedido " + this.getPedido());*/

		if (getPedido().getDestinos().size() == 0) {
			JSFUtil.retornarMensagemErro("Alerta", "Pedido deve ter pelo menos um destino", "messages");
			return;
		}

		// Se já existir um pedido no banco, então atualiza
		/*System.out.println("parou antes do if");*/
		
		if (this.getPedido().getNumeroPedido() == null) {

			adicionaUsuario();

			pedidoDao.adiciona(this.getPedido());
			JSFUtil.retornarMensagemInfo("Aviso", "Pedido realizado com sucesso!", "messages");

			this.pedidos = pedidoDao.listaTodos();
		} else {
			
			this.getPedido().setStatus(StatusEnum.PEDIDO_ENVIADO);
			pedidoDao.atualiza(this.getPedido());
			JSFUtil.retornarMensagemInfo("Aviso", "Pedido atualizado com sucesso!", "messages");

		}

		this.setPedido(new Pedido());
	}

	private void adicionaUsuario() {
		Usuario usuarioAtual = (Usuario) JSFUtil.getSessionMap().get("usuarioLogado");

		this.getPedido().adicionaUsuario(usuarioAtual);
	}

	@Transactional
	public void autorizarPedido() {
		System.out.println("Autorizando pedido");
		if (this.getPedido().getMotoristas().size() == 0) {
			FacesContext.getCurrentInstance().addMessage("pedido",
					new FacesMessage("Motorista deve ser incluido à autorização."));
			return;
		}
		if (this.getPedido().getVeiculos().size() == 0) {
			FacesContext.getCurrentInstance().addMessage("pedido",
					new FacesMessage("Veiculo deve ser incluido à autorização."));
			return;
		}
		System.out.println("mudando Status");
		
		this.getPedido().setStatus(StatusEnum.AUTORIZADO);
		pedidoDao.atualiza(this.getPedido());
		JSFUtil.retornarMensagemInfo("Aviso", "Pedido autorizado com sucesso!", "messages");

		this.setPedido(new Pedido());
	}

	@Transactional
	public void naoAutorizarPedido() {
		System.out.println("Negando Autorização de pedido ");
		if (this.getPedido().getJustificaIndeferimento() == null) {
			JSFUtil.retornarMensagemErro("Erro", "A justificativa deve ser incluída", "messages");
			return;
		}
		System.out.println("mudando Status");
		this.getPedido().setStatus(StatusEnum.NEGADO);

		pedidoDao.atualiza(this.getPedido());
		
		this.setPedido(new Pedido());
	}

	/*
	 * se status do pedido for autorizado deve ser inserido uma justificativa,
	 * caso contrário pode ser removido. lógica = se status autorizado troca
	 * para cancelado e atualiza o modelo no banco.
	 */

	@Transactional
	public void cancelar() {
		if (this.getPedido().getStatus() == StatusEnum.AUTORIZADO) {
			System.out.println("Cancelando pedido");

			// muda o status
			if(this.getPedido().getJustificaCancelamento() == null){
				JSFUtil.retornarMensagemErro("Alerta", "O cancelamento deve apresentar uma justificativa!", "messages");
				return;
			}
			this.getPedido().setStatus(StatusEnum.CANCELADO);
			pedidoDao.atualiza(this.getPedido());
		}
		if (this.getPedido().getStatus() == StatusEnum.PEDIDO_ENVIADO) {
			System.out.println("removendo pedido");
			pedidoDao.remove(pedido);
		}
		JSFUtil.retornarMensagemAviso("Aviso", "Pedido de transporte cancelado", "messages");
		this.pedidos = pedidoDao.listaTodos();

	}

	@Transactional
	public void declararStatusAtendido(Pedido pedido) {
		if(pedido.getStatus() != StatusEnum.AUTORIZADO) {
			FacesContext.getCurrentInstance().addMessage("pedido",
					new FacesMessage("Um pedido deve ser Autorizado antes de Atendido."));
			
			return;
		}
		System.out.println("Declando status como atendido");

		// muda o status
		pedido.setStatus(StatusEnum.DECLARADO_ATENDIDO);
		pedidoDao.atualiza(pedido);

	}
	@Transactional
	public void confirmarAtendimento(Pedido pedido){
		if(pedido.getStatus() != StatusEnum.DECLARADO_ATENDIDO) {
			FacesContext.getCurrentInstance().addMessage("pedido",
					new FacesMessage("Esse pedido não foi atendido."));
			
			return;
		}
		System.out.println("Confirmando atendimento");

		// muda o status
		pedido.setStatus(StatusEnum.ATENDIDO);
		pedidoDao.atualiza(pedido);
		
	}

	public String acaoAbrirFormPedido() {
		this.setPedido(new Pedido());

		return "formPedido?faces-redirect=true";

	}

	public String alterarPedido(Pedido pedido) {
		this.pedido = this.pedidoDao.buscaPedidoComDestinos(pedido.getNumeroPedido());
		return "formPedido?faces-redirect=true";
	}

	public void carregar(Pedido pedido) {
		System.out.println("Carregando pedido " + pedido.getNumeroPedido());
		this.pedido = this.pedidoDao.buscaPorId(pedido.getNumeroPedido());

	}

	public String formAutor() {
		System.out.println("Chamanda do formulário do Autor.");
		return "autor?faces-redirect=true";
	}

	// método para personalizar validação client-side
	/*
	 * public void comecaComDigitoUm(FacesContext fc, UIComponent component,
	 * Object value) throws ValidatorException {
	 * 
	 * String valor = value.toString(); if (!valor.startsWith("1")) { throw new
	 * ValidatorException(new FacesMessage("ISBN deveria começar com 1")); }
	 * 
	 * }
	 */

	public Destino getDestino() {
		return destino;
	}

	public void setDestino(Destino destino) {
		this.destino = destino;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void novoPedido() {
		this.setPedido(new Pedido());
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public TipoTransporteEnum[] getTiposDeTransporte() {
		return TipoTransporteEnum.values();
	}
	
	public EstadosEnum[] getEstados() {
		return EstadosEnum.values();
	}

	public Integer getMotoristaId() {
		return motoristaId;
	}

	public void setMotoristaId(Integer motoristaId) {
		this.motoristaId = motoristaId;
	}

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

}
