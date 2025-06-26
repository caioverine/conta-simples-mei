package com.contasimplesmei.dto

import java.math.BigDecimal

data class DashboardResumoDTO(
    val saldoAtual: BigDecimal,
    val receitaTotal: BigDecimal,
    val despesaTotal: BigDecimal,
    val resultadoMensal: BigDecimal,
)
