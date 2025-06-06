package com.contasimplesmei.dto

import java.math.BigDecimal

data class DespesaPorCategoriaDTO(
    val categoria: String,
    val valor: BigDecimal
)
