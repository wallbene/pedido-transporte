package br.com.unigranrio.transporte.bean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.unigranrio.transporte.dao.UsuarioDao;
import br.com.unigranrio.transporte.modelo.AutorizacaoEnum;
import br.com.unigranrio.transporte.modelo.Usuario;
import br.com.unigranrio.transporte.util.JSFUtil;

@SuppressWarnings("serial")
@Scope(scopeName="session")
@Controller
public class UsuarioBean implements Serializable {

	private Usuario usuario;

	@Inject
	private UsuarioDao dao;

	private Integer usuarioId;
	
	
	public UsuarioBean() {
        usuario = new Usuario();
        SecurityContext context = SecurityContextHolder.getContext();
        if (context instanceof SecurityContext){
            Authentication authentication = context.getAuthentication();
            if (authentication instanceof Authentication){
                usuario.setEmail(((User)authentication.getPrincipal()).getUsername());
            }
        }
    }
	
	

	public Integer getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}

	public void carregarUsuarioPelaId() {
		this.usuario = this.dao.buscaPorId(usuarioId);
	}
	
	public String acaoAbrirUsuario(){
		this.setUsuario(new Usuario());
		
		return "usuario?faces-redirect=true";
	}

	@Transactional
	public void gravar() {
		System.out.println("Gravando usuario " + this.usuario.getNome());

		if (this.usuario.getId() == null) {
			//atribui autorização de usuário
			this.usuario.setAutorizacao(AutorizacaoEnum.ROLE_USER);
			
			this.dao.adiciona(this.usuario);
			JSFUtil.retornarMensagemInfo("Mensagem","Usuário cadastrado com Sucesso!" , "messages");
		} else {
			this.dao.atualiza(this.usuario);
			JSFUtil.retornarMensagemInfo("Mensagem","Usuário atualizado com Sucesso!" , "messages");
		}

		this.usuario = new Usuario();

	}

	@Transactional
	public void remover(Usuario usuario) {
		System.out.println("Removendo usuario " + usuario.getNome());
		this.dao.remove(usuario);
		JSFUtil.retornarMensagemInfo("Mensagem","Usuário removido com Sucesso!" , "messages");
	}

	public List<Usuario> getUsuarios() {
		return this.dao.listaTodos();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}