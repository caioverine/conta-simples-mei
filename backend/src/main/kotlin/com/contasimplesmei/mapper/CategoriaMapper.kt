package com.contasimplesmei.mapper

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.model.Categoria

fun Categoria.toResponseDTO(): CategoriaResponseDTO =
    CategoriaResponseDTO(
        id = this.id!!,
        nome = this.nome!!,
        tipo = this.tipo!!
    )

fun CategoriaRequestDTO.toEntity(): Categoria =
    Categoria(
        id = null,
        nome = this.nome,
        tipo = this.tipo
    )