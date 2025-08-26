package com.contasimplesmei.dto

import java.math.BigDecimal

data class ResumoFinanceiroDTO(
    val totalReceitas: BigDecimal,
    val totalDespesas: BigDecimal,
    val totalTransacoes: Int,
) {
    val saldo: BigDecimal get() = totalReceitas - totalDespesas
}
