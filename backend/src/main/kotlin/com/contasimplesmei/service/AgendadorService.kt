package com.contasimplesmei.service

import com.contasimplesmei.repository.ReceitaRepository
import com.contasimplesmei.repository.UsuarioRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters
import java.util.logging.Logger

@Service
class AgendadorService(
    private val notificacaoService: NotificacaoService,
    private val usuarioRepository: UsuarioRepository,
    private val receitaRepository: ReceitaRepository
) {

    private val logger = Logger.getLogger(AgendadorService::class.java.name)

    // Executa todo dia 15 de cada mês às 9h
    @Scheduled(cron = "0 0 9 15 * ?")
    fun verificarVencimentoDAS() {
        logger.info("Iniciando verificação de vencimento do DAS")

        val proximoMes = LocalDateTime.now().plusMonths(1)
        val dataVencimento = proximoMes.with(TemporalAdjusters.lastDayOfMonth()).withDayOfMonth(20)

        usuarioRepository.findAll().forEach { usuario ->
            notificacaoService.criarLembreteDAS(usuario.id!!, dataVencimento)
        }
    }

    // Executa todos os domingos às 10h
    @Scheduled(cron = "0 0 10 ? * SUN")
    fun verificarLimiteMEI() {
        logger.info("Iniciando verificação de limite MEI")

        val inicioAno = LocalDateTime.now().withDayOfYear(1)

        usuarioRepository.findAll().forEach { usuario ->
            val receitaAcumulada = receitaRepository.findReceitaAcumuladaAnoByUsuarioId(
                usuario.id!!,
                inicioAno.toLocalDate(),
                LocalDateTime.now().toLocalDate()
            ) ?: 0.0

            notificacaoService.criarAlerteLimiteMEI(usuario.id!!, receitaAcumulada)
        }
    }
}