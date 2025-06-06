package com.contasimplesmei.dto

import java.math.BigDecimal

interface CategoriaDespesaGroupProjection {
    val categoria: String
    val total: BigDecimal
}