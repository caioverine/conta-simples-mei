package com.contasimplesmei.mapper

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.dto.ReceitaResponseDTO
import com.contasimplesmei.model.Receita
import java.util.UUID

fun Receita.toResponseDTO(): ReceitaResponseDTO =
    ReceitaResponseDTO(
        id = this.id!!,
        categoria = this.categoria,
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )

fun ReceitaRequestDTO.toEntity(): Receita =
    Receita(
        id = null,
        categoria = this.categoria,
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )