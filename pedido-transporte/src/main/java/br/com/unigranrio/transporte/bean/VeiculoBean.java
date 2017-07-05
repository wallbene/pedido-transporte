package br.com.unigranrio.transporte.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.unigranrio.transporte.dao.VeiculoDao;
import br.com.unigranrio.transporte.modelo.TipoVeiculoEnum;
import br.com.unigranrio.transporte.modelo.Veiculo;
import br.com.unigranrio.transporte.util.JSFUtil;

@SuppressWarnings("serial")
@Controller
public class VeiculoBean implements Serializable {

	private Veiculo veiculo = new Veiculo();

	@Inject
	private VeiculoDao dao;

	private Integer veiculoId;

	public Integer getVeiculoId() {
		return veiculoId;
	}

	public void setVeiculoId(Integer veiculoId) {
		this.veiculoId = veiculoId;
	}

	public void carregarVeiculoPelaId() {
		this.veiculo = this.dao.buscaPorId(veiculoId);
	}
	
	public String acaoAbrirVeiculo(){
		this.setVeiculo(new Veiculo());
		
		return "veiculo?faces-redirect=true";
	}

	@Transactional
	public void gravar() {
		System.out.println("Gravando veiculo " + this.veiculo.getModelo());

		if (this.veiculo.getId() == null) {
			this.dao.adiciona(this.veiculo);
			JSFUtil.retornarMensagemInfo("Mensagem","Veículo cadastrado com Sucesso!" , "messages");
		} else {
			this.dao.atualiza(this.veiculo);
			JSFUtil.retornarMensagemInfo("Mensagem","Veículo atualizado com Sucesso!" , "messages");
		}

		this.veiculo = new Veiculo();
	}

	@Transactional
	public void remover(Veiculo veiculo) {
		System.out.println("Removendo veiculo " + veiculo.getModelo());
		this.dao.remove(veiculo);
		JSFUtil.retornarMensagemInfo("Mensagem","Veículo removido com Sucesso!" , "messages");
	}
	
	public TipoVeiculoEnum[] getTiposDeVeiculo(){
		return TipoVeiculoEnum.values();
	}

	public List<Veiculo> getVeiculos() {
		return this.dao.listaTodos();
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
}