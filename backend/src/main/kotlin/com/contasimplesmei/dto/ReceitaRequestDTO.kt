package com.contasimplesmei.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class ReceitaRequestDTO(
    @field:NotNull val categoria: CategoriaRequestDTO,
    @field:NotBlank val descricao: String,
    @field:NotNull val valor: BigDecimal,
    @field:NotNull val data: LocalDate
)
