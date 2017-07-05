package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate(value=true)
public class Veiculo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="VEICULO_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "VEICULO_ID", sequenceName = "SEQ_VEICULO", allocationSize = 1)
	private Integer id;
	
	private String marca;
	private String modelo;
	private Integer ano;
	private String placa;
	
	@Enumerated(EnumType.STRING)
	private TipoVeiculoEnum tipoVeiculo;
	
	private Boolean situacao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa.toUpperCase();
	}
	public TipoVeiculoEnum getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(TipoVeiculoEnum tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public Boolean getSituacao() {
		return situacao;
	}
	public void setSituacao(Boolean situacao) {
		this.situacao = situacao;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
}
