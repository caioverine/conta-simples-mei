package com.contasimplesmei.dto

import com.contasimplesmei.enums.CategoriaDespesaEnum
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class DespesaResponseDTO(
    val id: UUID,
    val categoria: CategoriaDespesaEnum,
    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate
)
