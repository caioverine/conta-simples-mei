package com.contasimplesmei.dto

import java.math.BigDecimal

data class ResumoMensalDespesaDTO(
    val mes: Int,
    val ano: Int,
    val total: BigDecimal,
    val quantidade: Long,
)
