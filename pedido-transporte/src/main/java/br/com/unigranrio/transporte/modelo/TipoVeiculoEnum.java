package br.com.unigranrio.transporte.modelo;

public enum TipoVeiculoEnum {
	CARRO("Carro"), VAN("Van"), ONIBUS("Onibus"), CAMINHAO("Caminhão baú");
	
	private String label;

	private TipoVeiculoEnum(String tipo) {
		this.label =tipo;
		
	}

	public String getLabel() {
		return label;
	}
	
	
	
	
	

}
