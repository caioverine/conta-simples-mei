package com.contasimplesmei.service

import com.contasimplesmei.dto.DashboardResumoDTO
import com.contasimplesmei.dto.DespesaPorCategoriaDTO
import com.contasimplesmei.dto.SaldoMensalDTO
import com.contasimplesmei.dto.UltimaMovimentacaoDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.repository.DespesaRepository
import com.contasimplesmei.repository.ReceitaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.YearMonth

@Service
class DashboardService(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val receitaRepository: ReceitaRepository,
    private val despesaRepository: DespesaRepository
) {
    fun obterResumoFinanceiro(): DashboardResumoDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val totalReceitas = receitaRepository.sumValor(usuarioLogado.id!!) ?: BigDecimal.ZERO
        val totalDespesas = despesaRepository.sumValor(usuarioLogado.id!!) ?: BigDecimal.ZERO
        val saldoAtual = totalReceitas - totalDespesas
        val resultadoMensal = receitaRepository.sumByMesAtual(usuarioLogado.id!!) - despesaRepository.sumByMesAtual(usuarioLogado.id!!)

        return DashboardResumoDTO(
            saldoAtual = saldoAtual,
            receitaTotal = totalReceitas,
            despesaTotal = totalDespesas,
            resultadoMensal = resultadoMensal
        )
    }

    fun obterEvolucaoSaldoMensal(): List<SaldoMensalDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val meses = gerarUltimosMeses(3)
        val resultado = mutableListOf<SaldoMensalDTO>()
        var saldoAcumulado = BigDecimal.ZERO

        for (mes in meses) {
            val receitas = receitaRepository.sumByMes(usuarioLogado.id!!, mes.year, mes.monthValue) ?: BigDecimal.ZERO
            val despesas = despesaRepository.sumByMes(usuarioLogado.id!!, mes.year, mes.monthValue) ?: BigDecimal.ZERO
            saldoAcumulado += (receitas - despesas)

            resultado.add(SaldoMensalDTO("${mes.monthValue.toString().padStart(2, '0')}/${mes.year}", saldoAcumulado))
        }

        return resultado
    }

    fun obterDespesasPorCategoria(): List<DespesaPorCategoriaDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        return despesaRepository.sumGroupByCategoria(usuarioLogado.id!!)
            .map {
                DespesaPorCategoriaDTO(
                    categoria = it.categoria.toResponseDTO(),
                    valor = it.total
                )
            }
    }

    fun obterUltimasMovimentacoes(): List<UltimaMovimentacaoDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val receitas = receitaRepository.findTop5ByAtivoTrueAndUsuarioIdOrderByDataDesc(usuarioLogado.id!!)
            .map {
                UltimaMovimentacaoDTO(
                    data = it.data,
                    valor = it.valor,
                    descricao = it.descricao,
                    categoria = it.categoria.nome!!,
                    tipo = "Receita"
                )
            }

        val despesas = despesaRepository.findTop5ByAtivoTrueAndUsuarioIdOrderByDataDesc(usuarioLogado.id!!)
            .map {
                UltimaMovimentacaoDTO(
                    data = it.data,
                    valor = it.valor.negate(),
                    descricao = it.descricao,
                    categoria = it.categoria.nome!!,
                    tipo = "Despesa"
                )
            }

        return (receitas + despesas)
            .sortedByDescending { it.data }
            .take(5)
    }

    private fun gerarUltimosMeses(qtd: Int): List<YearMonth> {
        val agora = YearMonth.now()
        return (0 until qtd).map { agora.minusMonths((qtd - 1 - it).toLong()) }
    }
}