package com.contasimplesmei.dto

import java.math.BigDecimal

data class RelatorioAnualResponseDTO(
    val ano: Int,
    val faturamentoTotal: BigDecimal,
    val despesasTotal: BigDecimal,
    val lucroTotal: BigDecimal,
    // TODO
//    val dasTotal: BigDecimal,
//    val dasPago: BigDecimal,
//    val dasPendente: BigDecimal,
    val limiteFaturamento: BigDecimal,
    val percentualLimiteUsado: BigDecimal,
//    val meses: BigDecimal,
)
