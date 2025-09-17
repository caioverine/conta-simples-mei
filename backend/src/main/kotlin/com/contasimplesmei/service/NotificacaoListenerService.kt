package com.contasimplesmei.service

import com.contasimplesmei.dto.NotificacaoDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.data.redis.connection.Message
import org.springframework.data.redis.connection.MessageListener
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class NotificacaoListenerService(
    private val objectMapper: ObjectMapper,
    // TODO implementar EmailService
    // private val emailService: EmailService
) : MessageListener {

    private val logger = Logger.getLogger(NotificacaoListenerService::class.java.name)

    override fun onMessage(message: Message, pattern: ByteArray?) {
        try {
            val notificacao = objectMapper.readValue(message.body, NotificacaoDTO::class.java)
            processarNotificacao(notificacao)
        } catch (e: Exception) {
            logger.severe("Erro ao processar notificação: ${e.message}")
        }
    }

    private fun processarNotificacao(notificacao: NotificacaoDTO) {
        logger.info("Processando notificação: ${notificacao.titulo} para usuário ${notificacao.usuarioId}")

        // TODO implementar diferentes ações:
        // 1. Salvar no banco para exibir no frontend
        // 2. Enviar email/SMS
        // 3. Push notification
        // 4. WebSocket para notificação em tempo real

        when (notificacao.tipo) {
            com.contasimplesmei.enums.TipoNotificacaoEnum.DAS_VENCIMENTO -> {
                processarLembreteDAS(notificacao)
            }
            com.contasimplesmei.enums.TipoNotificacaoEnum.LIMITE_MEI_PROXIMO -> {
                processarAlerteLimite(notificacao)
            }
            else -> {
                // TODO Processar outras notificações
            }
        }
    }

    private fun processarLembreteDAS(notificacao: NotificacaoDTO) {
        // TODO Implementar lógica específica para lembrete DAS
        // Ex: enviar email, salvar notificação, etc.
        logger.info("Lembrete DAS processado para usuário ${notificacao.usuarioId}")
    }

    private fun processarAlerteLimite(notificacao: NotificacaoDTO) {
        // TODO Implementar lógica específica para alerta de limite
        logger.info("Alerta de limite MEI processado para usuário ${notificacao.usuarioId}")
    }
}