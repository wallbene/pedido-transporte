package br.com.unigranrio.transporte.modelo;

public enum TipoTransporteEnum {
	
	CARGA("Carga"), PESSOA("Pessoas");

	private String label;

	private TipoTransporteEnum(String tipo) {
		this.label =tipo;
		
	}

	public String getLabel() {
		return label;
	}
}
