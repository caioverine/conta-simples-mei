package com.contasimplesmei.enums

enum class TipoNotificacaoEnum(val descricao: String) {
    DAS_VENCIMENTO("Lembrete de vencimento do DAS"),
    LIMITE_MEI_PROXIMO("Aproximação do limite anual MEI"),
    BACKUP_DADOS("Lembrete para backup dos dados"),
    LANCAMENTO_PENDENTE("Lançamento pendente de confirmação"),
    META_MENSAL("Acompanhamento de meta mensal")
}