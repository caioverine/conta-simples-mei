package com.contasimplesmei.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

data class ReceitaRequestDTO(
    @field:NotNull val idCategoria: UUID,
    @field:NotBlank val descricao: String,
    @field:NotNull val valor: BigDecimal,
    @field:NotNull val data: LocalDate,
)
