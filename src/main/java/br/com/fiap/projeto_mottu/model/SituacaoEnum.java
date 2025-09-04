package br.com.fiap.projeto_mottu.model;

public enum SituacaoEnum {
	EM_MANUTENCAO("Em manutenção"), EM_FUNCIONAMENTO("Em funcionamento"), NO_PATIO("No pátio");
	
	private final String descricao;

	SituacaoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
