package com.contasimplesmei.dto

import java.util.UUID

data class CategoriaResponseDTO(
    val id: UUID,
    val nome: String,
    val tipo: String
)
