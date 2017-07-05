package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

@Entity
@DynamicUpdate(value=true)
public class Usuario implements Autenticavel, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="USUARIO_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "USUARIO_ID", sequenceName = "SEQ_USUARIO", allocationSize = 1)	
	private Integer id;
	
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	
	@Column(name = "enable", columnDefinition = "BOOLEAN")
    private Boolean enable;
	
	@Enumerated(EnumType.STRING)
    private AutorizacaoEnum autorizacao;
	
	@Temporal(TemporalType.DATE)
	private Calendar dataNascimento = Calendar.getInstance();
	
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

	public Calendar getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Calendar dtNascimento) {
		this.dataNascimento = dtNascimento;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public AutorizacaoEnum getAutorizacao() {
		return autorizacao;
	}

	public void setAutorizacao(AutorizacaoEnum autorizacao) {
		this.autorizacao = autorizacao;
	}

}
