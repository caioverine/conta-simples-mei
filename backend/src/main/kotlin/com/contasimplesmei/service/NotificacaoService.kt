package com.contasimplesmei.service

import com.contasimplesmei.dto.NotificacaoDTO
import com.contasimplesmei.enums.TipoNotificacaoEnum
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.UUID

@Service
class NotificacaoService(
    private val redisTemplate: RedisTemplate<String, Any>,
    private val objectMapper: ObjectMapper
) {

    @Value("\${notification.channels.das-reminder}")
    private lateinit var dasReminderChannel: String

    @Value("\${notification.channels.limite-mei}")
    private lateinit var limiteMeiChannel: String

    @Value("\${notification.channels.backup}")
    private lateinit var backupChannel: String

    fun enviarNotificacao(notificacao: NotificacaoDTO) {
        val channel = when (notificacao.tipo) {
            TipoNotificacaoEnum.DAS_VENCIMENTO -> dasReminderChannel
            TipoNotificacaoEnum.LIMITE_MEI_PROXIMO -> limiteMeiChannel
            TipoNotificacaoEnum.BACKUP_DADOS -> backupChannel
            else -> "general-notifications"
        }

        redisTemplate.convertAndSend(channel, notificacao)
    }

    fun criarLembreteDAS(usuarioId: UUID, dataVencimento: LocalDateTime) {
        val notificacao = NotificacaoDTO(
            usuarioId = usuarioId,
            tipo = TipoNotificacaoEnum.DAS_VENCIMENTO,
            titulo = "Vencimento do DAS",
            mensagem = "Seu DAS vence em ${dataVencimento.dayOfMonth}/${dataVencimento.monthValue}. " +
                    "Não esqueça de efetuar o pagamento até o dia 20!",
            dadosAdicionais = mapOf(
                "dataVencimento" to dataVencimento.toString(),
                "valorEstimado" to calcularValorDAS(usuarioId)
            )
        )
        enviarNotificacao(notificacao)
    }

    fun criarAlerteLimiteMEI(usuarioId: UUID, receitaAcumulada: Double) {
        val limiteAnual = 81000.0
        val percentual = (receitaAcumulada / limiteAnual) * 100

        if (percentual >= 80) {
            val notificacao = NotificacaoDTO(
                usuarioId = usuarioId,
                tipo = TipoNotificacaoEnum.LIMITE_MEI_PROXIMO,
                titulo = "Atenção: Limite MEI",
                mensagem = "Você já utilizou ${String.format("%.1f", percentual)}% do limite anual MEI. " +
                        "Faltam R$ ${String.format("%.2f", limiteAnual - receitaAcumulada)} para o limite.",
                dadosAdicionais = mapOf(
                    "receitaAtual" to receitaAcumulada,
                    "limiteAnual" to limiteAnual,
                    "percentualUtilizado" to percentual
                )
            )
            enviarNotificacao(notificacao)
        }
    }

    private fun calcularValorDAS(usuarioId: UUID): Double {
        // TODO Implementar cálculo baseado no tipo de negócio do usuário
        // Por enquanto, valor fixo
        return 67.00 // Valor aproximado para serviços em 2025
    }
}