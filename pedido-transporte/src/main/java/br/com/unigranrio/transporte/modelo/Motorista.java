package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@DynamicUpdate(value=true)
public class Motorista implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="MOTORISTA_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "MOTORISTA_ID", sequenceName = "SEQ_MOTORISTA", allocationSize = 1)
	private Integer id;
	
	@NotEmpty(message="Nome deve ser informado")
	private String nome;
	
	@Column(unique=true, length=40)
	private String email;

	private String cpf;
	
	@Column(nullable=false, unique= true, length=11)
	@NotEmpty(message="cnh deve ser informado")
	private String cnh;
	
	@Enumerated(EnumType.STRING)
	private CategoriaCnhEnum categoria;
	
	@Temporal(TemporalType.DATE)
	private Calendar validade = Calendar.getInstance();
	
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento = Calendar.getInstance();
	
	@NotEmpty(message="Nome deve ser informado")
	private String telefone;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCnh() {
		return cnh;
	}

	public void setCnh(String cnh) {
		this.cnh = cnh;
	}

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public CategoriaCnhEnum getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaCnhEnum categoria) {
		this.categoria = categoria;
	}

	public Calendar getValidade() {
		return validade;
	}

	public void setValidade(Calendar validade) {
		this.validade = validade;
	}

}
