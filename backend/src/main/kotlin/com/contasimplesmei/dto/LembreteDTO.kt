package com.contasimplesmei.dto

import com.contasimplesmei.enums.TipoNotificacaoEnum
import java.time.LocalDateTime
import java.util.UUID

data class LembreteDTO(
    val usuarioId: UUID,
    val tipo: TipoNotificacaoEnum,
    val dataVencimento: LocalDateTime,
    val valor: Double? = null,
    val descricao: String
)
