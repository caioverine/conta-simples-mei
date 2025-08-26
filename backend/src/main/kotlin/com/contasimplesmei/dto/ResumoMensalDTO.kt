package com.contasimplesmei.dto

import java.math.BigDecimal

data class ResumoMensalDTO(
    val mes: Int,
    val ano: Int,
    val totalReceitas: BigDecimal,
    val totalDespesas: BigDecimal,
) {
    val saldo: BigDecimal get() = totalReceitas - totalDespesas
}
