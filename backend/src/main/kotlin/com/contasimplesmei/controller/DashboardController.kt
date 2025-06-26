package com.contasimplesmei.controller

import com.contasimplesmei.dto.DashboardResumoDTO
import com.contasimplesmei.dto.DespesaPorCategoriaDTO
import com.contasimplesmei.dto.SaldoMensalDTO
import com.contasimplesmei.dto.UltimaMovimentacaoDTO
import com.contasimplesmei.service.DashboardService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dashboard")
class DashboardController(
    private val dashboardService: DashboardService,
) {
    @GetMapping("/resumo")
    fun getResumoFinanceiro(): DashboardResumoDTO = dashboardService.obterResumoFinanceiro()

    @GetMapping("/saldo-mensal")
    fun getEvolucaoSaldo(): List<SaldoMensalDTO> = dashboardService.obterEvolucaoSaldoMensal()

    @GetMapping("/despesas-categoria")
    fun getDespesasPorCategoria(): List<DespesaPorCategoriaDTO> = dashboardService.obterDespesasPorCategoria()

    @GetMapping("/ultimas-movimentacoes")
    fun getUltimasMovimentacoes(): List<UltimaMovimentacaoDTO> = dashboardService.obterUltimasMovimentacoes()
}
