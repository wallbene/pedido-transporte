package br.com.unigranrio.transporte.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.springframework.stereotype.Controller;

import br.com.unigranrio.transporte.dao.AdministradorDao;
import br.com.unigranrio.transporte.dao.UsuarioDao;
import br.com.unigranrio.transporte.modelo.Administrador;
import br.com.unigranrio.transporte.modelo.Usuario;

@Controller
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String senha;
	

	@Inject
	UsuarioDao usuarioDao;
	
	@Inject
	AdministradorDao admDao;
	

	public String efetuaLogin() {
		
		/*System.out.println("fazendo login do usuario "
				+ this.usuario.getEmail());*/

		FacesContext context = FacesContext.getCurrentInstance();

		Usuario usuario = usuarioDao.existe(this.email, this.senha);
		if (usuario != null) {
			
			context.getExternalContext().getSessionMap()
					.put("usuarioLogado", usuario);
			return "homeUsuario?faces-redirect=true";
		}
		Administrador adm = admDao.existe(this.email, this.senha);
		if (adm != null) {
			
			context.getExternalContext().getSessionMap()
					.put("adminLogado", adm);
			return "homeAdmin?faces-redirect=true";
		}
		
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));

		return "login?faces-redirect=true";
	}

	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
}
