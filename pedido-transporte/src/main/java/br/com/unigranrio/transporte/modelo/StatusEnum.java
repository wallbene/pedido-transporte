package br.com.unigranrio.transporte.modelo;

public enum StatusEnum {
	PEDIDO_ENVIADO("Aguardando autorização"), AUTORIZADO("Autorizado"), CANCELADO("Cancelado"), NEGADO("Não autorizado"), DECLARADO_ATENDIDO("Aguardando confirmação de atendimento"), ATENDIDO("Atendido");
	
	private String nome;

	private StatusEnum(String nome) {
		this.setNome(nome);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}