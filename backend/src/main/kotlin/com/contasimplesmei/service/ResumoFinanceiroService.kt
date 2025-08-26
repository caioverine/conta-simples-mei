package com.contasimplesmei.service

import com.contasimplesmei.dto.ResumoFinanceiroDTO
import com.contasimplesmei.dto.ResumoMensalDTO
import com.contasimplesmei.repository.DespesaRepository
import com.contasimplesmei.repository.ReceitaRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Service
class ResumoFinanceiroService(
    private val despesaRepository: DespesaRepository,
    private val receitaRepository: ReceitaRepository,
) {
    /**
     * Calcula resumo financeiro combinando dados de despesas e receitas
     */
    fun calcularResumoFinanceiroPorPeriodo(
        usuarioId: UUID,
        dataInicio: LocalDate,
        dataFim: LocalDate,
    ): ResumoFinanceiroDTO {
        val totalReceitas = receitaRepository.calcularTotalReceitasPorPeriodo(
            usuarioId, dataInicio, dataFim,
        )

        val totalDespesas = despesaRepository.calcularTotalDespesasPorPeriodo(
            usuarioId, dataInicio, dataFim,
        )

        val receitas = receitaRepository.findByUsuarioIdAndDataBetween(
            usuarioId, dataInicio, dataFim,
        )

        val despesas = despesaRepository.findByUsuarioIdAndDataBetween(
            usuarioId, dataInicio, dataFim,
        )

        val totalTransacoes = receitas.size + despesas.size

        return ResumoFinanceiroDTO(
            totalReceitas = totalReceitas,
            totalDespesas = totalDespesas,
            totalTransacoes = totalTransacoes,
        )
    }

    /**
     * Obtém resumo dos últimos meses combinando receitas e despesas
     */
    fun obterResumoUltimosMeses(
        usuarioId: UUID,
        dataInicio: LocalDate,
    ): List<ResumoMensalDTO> {
        val resumoReceitas = receitaRepository.obterResumoMensalReceitas(usuarioId, dataInicio)
        val resumoDespesas = despesaRepository.obterResumoMensalDespesas(usuarioId, dataInicio)

        // Combina os resumos mensais por mês/ano
        val resumoMap = mutableMapOf<Pair<Int, Int>, ResumoMensalDTO>()

        // Adiciona receitas
        resumoReceitas.forEach { receita ->
            val chave = Pair(receita.mes, receita.ano)
            resumoMap[chave] = ResumoMensalDTO(
                mes = receita.mes,
                ano = receita.ano,
                totalReceitas = receita.total,
                totalDespesas = BigDecimal.ZERO,
            )
        }

        // Adiciona despesas
        resumoDespesas.forEach { despesa ->
            val chave = Pair(despesa.mes, despesa.ano)
            val resumoExistente = resumoMap[chave]

            if (resumoExistente != null) {
                resumoMap[chave] = resumoExistente.copy(totalDespesas = despesa.total)
            } else {
                resumoMap[chave] = ResumoMensalDTO(
                    mes = despesa.mes,
                    ano = despesa.ano,
                    totalReceitas = BigDecimal.ZERO,
                    totalDespesas = despesa.total,
                )
            }
        }

        return resumoMap.values
            .sortedWith(compareByDescending<ResumoMensalDTO> { it.ano }
                .thenByDescending { it.mes })
    }
}