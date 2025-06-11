package com.contasimplesmei.dto

import com.contasimplesmei.enums.TipoCategoria

data class CategoriaRequestDTO(
    val nome: String,
    val tipo: TipoCategoria
)