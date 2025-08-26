package com.contasimplesmei.service

import com.contasimplesmei.dto.RelatorioAnualResponseDTO
import com.contasimplesmei.dto.RelatorioMensalResponseDTO
import com.contasimplesmei.dto.ResumoFinanceiroDTO
import com.contasimplesmei.enums.TipoNegocioEnum
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@Service
class RelatorioService(
    @Autowired private val usuarioService: UsuarioService,
    @Autowired private val resumoFinanceiroService: ResumoFinanceiroService,
) {
    companion object {
        val VALORES_DAS = mapOf(
            TipoNegocioEnum.COMERCIO to BigDecimal("61.60"),
            TipoNegocioEnum.INDUSTRIA to BigDecimal("61.60"),
            TipoNegocioEnum.SERVICOS to BigDecimal("65.60"),
            TipoNegocioEnum.MISTO to BigDecimal("66.60")
        )
        val LIMITE_FATURAMENTO_MEI = BigDecimal("81000.00")
    }

    fun gerarRelatorioMensal(usuarioID: UUID, mes: Int, ano: Int): RelatorioMensalResponseDTO {
        val usuario = usuarioService.buscarPorId(usuarioID)
            ?: throw IllegalArgumentException("Usuário não encontrado")

        // Calcula período
        val dataInicio = LocalDate.of(ano, mes, 1)
        val dataFim = dataInicio.withDayOfMonth(dataInicio.lengthOfMonth())

        // Busca transações do período usando query otimizada
        val resumoFinanceiro = resumoFinanceiroService.calcularResumoFinanceiroPorPeriodo(
            usuarioID, dataInicio, dataFim
        )

        // Calcula DAS
        val valorDas = calcularDas(usuario.tipoNegocio, usuario.temFuncionario)
        val vencimentoDas = calcularVencimentoDas(mes, ano)

        // TODO Verifica se DAS foi pago (apenas isso fica no banco)
//        val pagamentoDas = usuario.pagamentosDas.find {
//            it.mes == mes && it.ano == ano
//        }

        return RelatorioMensalResponseDTO(
            mes = mes,
            ano = ano,
            periodo = String.format("%02d/%d", mes, ano),
            tipoNegocio = usuario.tipoNegocio.descricao,
            resumoFinanceiro = ResumoFinanceiroDTO(
                totalReceitas = resumoFinanceiro.totalReceitas,
                totalDespesas = resumoFinanceiro.totalDespesas,
                totalTransacoes = resumoFinanceiro.totalTransacoes,
            ),
            // TODO
//            das = InfosDasDTO(
//                valor = valorDas,
//                vencimento = vencimentoDas,
//                pago = pagamentoDas?.pago ?: false,
//                dataPagamento = pagamentoDas?.dataPagamento,
//                situacao = if (pagamentoDas?.pago == true) "PAGO" else "PENDENTE",
//                emAtraso = pagamentoDas?.pago != true && LocalDate.now().isAfter(vencimentoDas)
//            ),
            calculadoEm = LocalDateTime.now()
        )
    }

    fun gerarResumoAnual(usuarioId: UUID, ano: Int): RelatorioAnualResponseDTO {
        val dataInicio = LocalDate.of(ano, 1, 1)
        val dataFim = LocalDate.of(ano, 12, 31)

        val usuario = usuarioService.buscarPorId(usuarioId)
            ?: throw IllegalArgumentException("Usuário não encontrado")

        // Resumo anual em uma única query
        val resumoAnual = resumoFinanceiroService.calcularResumoFinanceiroPorPeriodo(
            usuarioId, dataInicio, dataFim
        )

        // Resumo mensal para cada mês
        val dadosMensais = (1..12).map { mes ->
            try {
                val relatorioMes = gerarRelatorioMensal(usuarioId, mes, ano)
                RelatorioMensalResponseDTO(
                    mes = mes,
                    ano = ano,
                    periodo = String.format("%02d/%d", mes, ano),
                    resumoFinanceiro = ResumoFinanceiroDTO(
                        totalReceitas = relatorioMes.resumoFinanceiro.totalReceitas,
                        totalDespesas = relatorioMes.resumoFinanceiro.totalDespesas,
                        totalTransacoes = relatorioMes.resumoFinanceiro.totalTransacoes,
                    ),
                    tipoNegocio = usuario.tipoNegocio.descricao,
                    calculadoEm = LocalDateTime.now(),
                )
            } catch (e: Exception) {
                // Mês sem dados
                RelatorioMensalResponseDTO(
                    mes = mes,
                    ano = ano,
                    periodo = String.format("%02d/%d", mes, ano),
                    resumoFinanceiro = ResumoFinanceiroDTO(
                        totalReceitas = BigDecimal.ZERO,
                        totalDespesas = BigDecimal.ZERO,
                        totalTransacoes = 0,
                    ),
                    tipoNegocio = usuario.tipoNegocio.descricao,
                    calculadoEm = LocalDateTime.now(),
                )
            }
        }

        return RelatorioAnualResponseDTO(
            ano = ano,
            faturamentoTotal = resumoAnual.totalReceitas,
            despesasTotal = resumoAnual.totalDespesas,
            lucroTotal = resumoAnual.totalReceitas - resumoAnual.totalDespesas,
            // TODO
//            dasTotal = dadosMensais.sumOf { it.das.valor },
//            dasPago = dadosMensais.filter { it.das.pago }.sumOf { it.das.valor },
//            dasPendente = dadosMensais.filter { !it.das.pago }.sumOf { it.das.valor },
            limiteFaturamento = LIMITE_FATURAMENTO_MEI,
            percentualLimiteUsado = calcularPercentualLimite(resumoAnual.totalReceitas),
//            meses = dadosMensais
        )
    }

    private fun calcularDas(tipoNegocio: TipoNegocioEnum, temFuncionario: Boolean): BigDecimal {
        var valor = VALORES_DAS[tipoNegocio] ?: VALORES_DAS[TipoNegocioEnum.SERVICOS]!!
        if (temFuncionario) {
            valor = valor.add(BigDecimal("70.60")) // 5% do salário mínimo
        }
        return valor
    }

    private fun calcularVencimentoDas(mes: Int, ano: Int): LocalDate {
        return if (mes == 12) LocalDate.of(ano + 1, 1, 20)
        else LocalDate.of(ano, mes + 1, 20)
    }

    private fun calcularMargemLucro(receitas: BigDecimal, despesas: BigDecimal): BigDecimal {
        if (receitas <= BigDecimal.ZERO) return BigDecimal.ZERO
        return (receitas - despesas).divide(receitas, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
    }

    private fun calcularPercentualLimite(faturamento: BigDecimal): BigDecimal {
        return faturamento.divide(LIMITE_FATURAMENTO_MEI, 4, RoundingMode.HALF_UP)
            .multiply(BigDecimal("100"))
    }
}