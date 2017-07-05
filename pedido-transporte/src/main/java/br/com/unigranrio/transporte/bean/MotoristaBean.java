package br.com.unigranrio.transporte.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.unigranrio.transporte.dao.MotoristaDao;
import br.com.unigranrio.transporte.modelo.CategoriaCnhEnum;
import br.com.unigranrio.transporte.modelo.Motorista;
import br.com.unigranrio.transporte.util.JSFUtil;

@SuppressWarnings("serial")
@Controller
public class MotoristaBean implements Serializable {

	private Motorista motorista = new Motorista();

	@Inject
	private MotoristaDao dao;

	private Integer motoristaId;

	public Integer getMotoristaId() {
		return motoristaId;
	}

	public void setMotoristaId(Integer motoristaId) {
		this.motoristaId = motoristaId;
	}

	public void carregarMotoristaPelaId() {
		this.motorista = this.dao.buscaPorId(motoristaId);
	}
	
	public String acaoAbrirMotorista(){
		this.setMotorista(new Motorista());
		
		return "motorista?faces-redirect=true";
	}

	@Transactional
	public void gravar() {
		System.out.println("Gravando motorista " + this.motorista.getNome());

		if (this.motorista.getId() == null) {
			this.dao.adiciona(this.motorista);
			JSFUtil.retornarMensagemInfo("Mensagem","Motorista cadastrado com Sucesso!" , "messages");
		} else {
			this.dao.atualiza(this.motorista);
			JSFUtil.retornarMensagemInfo("Mensagem","Motorista atualizado com Sucesso!" , "messages");
		}

		this.motorista = new Motorista();
	}

	@Transactional
	public void remover(Motorista motorista) {
		System.out.println("Removendo motorista " + motorista.getNome());
		
		try {
			this.dao.remove(motorista);
			
			JSFUtil.retornarMensagemInfo("Mensagem","Motorista Exluído com Sucesso!" , "messages");
		} catch (Exception e) {
			System.out.println("não Exclui");
			
			JSFUtil.retornarMensagemInfo("Erro","Motorista não pode ser Exluído!" , "messages");
			
		}
			
	}

	public List<Motorista> getMotoristas() {
		return this.dao.listaTodos();
	}

	public Motorista getMotorista() {
		return motorista;
	}

	public void setMotorista(Motorista motorista) {
		this.motorista = motorista;
	}
	
	public CategoriaCnhEnum[] getCategoriasCnh() {
		return CategoriaCnhEnum.values();
	}
	
	
}