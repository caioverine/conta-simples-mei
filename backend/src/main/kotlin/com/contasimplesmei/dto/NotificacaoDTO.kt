package com.contasimplesmei.dto

import com.contasimplesmei.enums.TipoNotificacaoEnum
import java.time.LocalDateTime
import java.util.UUID

data class NotificacaoDTO(
    val id: UUID = UUID.randomUUID(),
    val usuarioId: UUID,
    val tipo: TipoNotificacaoEnum,
    val titulo: String,
    val mensagem: String,
    val dataEnvio: LocalDateTime = LocalDateTime.now(),
    val lida: Boolean = false,
    val dadosAdicionais: Map<String, Any> = emptyMap()
)
