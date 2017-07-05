package br.com.unigranrio.transporte.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class Administrador implements Autenticavel, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="ADMINISTRADOR_ID", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "ADMINISTRADOR_ID", sequenceName = "SEQ_ADMINISTRADOR", allocationSize = 1)
	private Integer id;
	
	private String nome;
	
	private String email;
	private String senha;
	
	private String cpf;
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
