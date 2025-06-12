package com.contasimplesmei.dto

import com.contasimplesmei.model.Categoria
import java.math.BigDecimal

interface CategoriaDespesaGroupProjection {
    val categoria: Categoria
    val total: BigDecimal
}