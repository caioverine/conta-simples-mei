package com.contasimplesmei.mapper

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.model.Despesa

fun Despesa.toResponseDTO(): DespesaResponseDTO =
    DespesaResponseDTO(
        id = this.id!!,
        categoria = this.categoria.toResponseDTO(),
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )

fun DespesaRequestDTO.toEntity(): Despesa =
    Despesa(
        id = null,
        categoria = this.categoria.toEntity(),
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )