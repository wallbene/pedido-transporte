package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;


@Entity
@DynamicUpdate(value=true)
public class Pedido implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="PEDIDO_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "PEDIDO_ID", sequenceName = "SEQ_PEDIDO", allocationSize = 1)
	private Integer numeroPedido;
	
	//atributo novo
	@Temporal(TemporalType.DATE)
	private Calendar dataPedido = Calendar.getInstance();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataInicial = Calendar.getInstance();
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dataFinal = Calendar.getInstance();
	
	@Enumerated(EnumType.STRING)
	private StatusEnum status = StatusEnum.PEDIDO_ENVIADO;
	
	private String observacao;
	
	@Enumerated(EnumType.STRING)
	private TipoTransporteEnum tipoTransporte;
	
	private int qtdPassageiros;
	
	// defaul null - se o Adm não autorizar o pedido, o mesmo deve preencher a justificativa.
	private String justificaIndeferimento;
	
	private String justificaCancelamento;
	
	@ManyToOne
	private Usuario usuario;
	
	//EAGER
	@JoinColumn(name="pedido_numeroPedido")
	@OneToMany(cascade= CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Destino> destinos = new ArrayList<Destino>();
	
	//EAGER
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Motorista> motoristas = new ArrayList<Motorista>();
	
	//EAGER
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Veiculo> veiculos = new ArrayList<Veiculo>();
	
	/*// Um pedido deverá ter informações dos administradores? 
	 * Talvez quando for implementado o módulo de relatório seja
	 *  importante nas imformações de relatórios constar quem autorizou	
	 * EAGER
	@ManyToMany 
	private List<Administrador> administradores = new ArrayList<Administrador>();
	*/
	
	public void adicionaDestino(Destino destino){
		this.destinos.add(destino);
	}
	public void removeDestino(Destino destino) {
		this.destinos.remove(destino);
	}
	
	public void adicionaMotorista(Motorista motorista){
		this.motoristas.add(motorista);
	}
	public void removeMotorista(Motorista motorista) {
		this.motoristas.remove(motorista);
	}
	
	public void adicionaVeiculo(Veiculo veiculo){
		this.veiculos.add(veiculo);
	}
	public void removeVeiculo(Veiculo veiculo) {
		this.veiculos.remove(veiculo);
	}
	

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer id) {
		this.numeroPedido = id;
	}
	public Calendar getDataPedido() {
		return dataPedido;
	}

	public Calendar getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Calendar dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Calendar getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Calendar dataFinal) {
		this.dataFinal = dataFinal;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Destino> getDestinos() {
		return destinos;
	}

	public void setDestinos(List<Destino> destinos) {
		this.destinos = destinos;
	}

	public List<Motorista> getMotoristas() {
		return motoristas;
	}

	public void setMotoristas(List<Motorista> motoristas) {
		this.motoristas = motoristas;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}
	
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public TipoTransporteEnum getTipoTransporte() {
		return tipoTransporte;
	}
	public void setTipoTransporte(TipoTransporteEnum tipoTransporte) {
		this.tipoTransporte = tipoTransporte;
	}
	public int getQtdPassageiros() {
		return qtdPassageiros;
	}
	public void setQtdPassageiros(int qtdPassageiros) {
		this.qtdPassageiros = qtdPassageiros;
	}
	public void adicionaUsuario(Usuario usuarioAtual) {
		this.usuario = usuarioAtual;
	}
	public String getJustificaCancelamento() {
		return justificaCancelamento;
	}
	public void setJustificaCancelamento(String justificaCancelamento) {
		this.justificaCancelamento = justificaCancelamento;
	}
	public String getJustificaIndeferimento() {
		return justificaIndeferimento;
	}
	public void setJustificaIndeferimento(String justificaIndeferimento) {
		this.justificaIndeferimento = justificaIndeferimento;
	}

	
}
