package com.contasimplesmei.dto

import java.math.BigDecimal
import java.time.LocalDate

data class InfosDasDTO(
    val valor: BigDecimal,
    val vencimento: LocalDate,
    val pago: Boolean,
    val situacao: String,
    val emAtraso: Boolean,
)
