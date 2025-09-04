package br.com.fiap.projeto_mottu.model;

public enum FuncaoEnum {
	ADMIN("Admin"),
	SUPERVISOR_OPERACOES("Supervisor de Operações"),
    ASSISTENTE_LOGISTICA("Assistente de Logística"),
    MECANICO_MOTOS("Mecânico de Motos"),
    AUXILIAR_OFICINA("Auxiliar de Oficina"),
    CONTROLADOR_FROTA("Controlador de Frota"),
    RESPONSAVEL_VISTORIA("Responsável por Vistoria"),
    ATENDENTE_FILIAL("Atendente de Filial"),
    ANALISTA_SUPORTE_CLIENTE("Analista de Suporte ao Cliente"),
    CONSULTOR_ONBOARDING("Consultor de Onboarding"),
    ESPECIALISTA_RETENCAO("Especialista em Retenção"),
    SEGURANCA_PATRIMONIAL("Segurança Patrimonial"),
    ANALISTA_RISCO("Analista de Risco"),
    FISCAL_PATIO("Fiscal de Pátio"),
    GERENTE_FILIAL("Gerente de Filial"),
    COORDENADOR_REGIONAL("Coordenador Regional"),
    ANALISTA_FINANCEIRO("Analista Financeiro"),
    ANALISTA_DADOS_OPERACIONAIS("Analista de Dados Operacionais"),
    PROMOTOR_PARCEIRAS("Promotor de Parcerias"),
    ANALISTA_MARKETING_LOCAL("Analista de Marketing Local"),
    ESPECIALISTA_EXPANSAO("Especialista em Expansão");

    private final String descricao;

    FuncaoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
