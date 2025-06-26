package com.contasimplesmei.dto

import java.math.BigDecimal
import java.time.LocalDate

data class UltimaMovimentacaoDTO(
    val data: LocalDate,
    val valor: BigDecimal,
    val descricao: String,
    val categoria: String,
    val tipo: String,
)
