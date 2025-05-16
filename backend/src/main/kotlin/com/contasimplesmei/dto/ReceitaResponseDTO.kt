package com.contasimplesmei.dto

import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class ReceitaResponseDTO(
    val id: UUID,
    val categoria: String,
    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate
)