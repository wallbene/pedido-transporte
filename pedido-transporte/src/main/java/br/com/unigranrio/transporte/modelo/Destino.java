package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Destino implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="DESTINO_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "DESTINO_ID", sequenceName = "SEQ_DESTINO", allocationSize = 1)
	private Integer id;
	
	private String descricao;
	private String logradouro;
	private String complemento;
	private String cidade;
	private String cep;
	
	@Enumerated(EnumType.STRING)
	private EstadosEnum uf;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public EstadosEnum getUf() {
		return uf;
	}

	public void setUf(EstadosEnum uf) {
		this.uf = uf;
	}

}