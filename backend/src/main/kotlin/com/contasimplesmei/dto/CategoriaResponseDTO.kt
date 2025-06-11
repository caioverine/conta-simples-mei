package com.contasimplesmei.dto

import com.contasimplesmei.enums.TipoCategoria
import java.util.UUID

data class CategoriaResponseDTO(
    val id: UUID,
    val nome: String,
    val tipo: TipoCategoria
)
