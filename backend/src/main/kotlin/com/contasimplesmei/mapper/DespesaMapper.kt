package com.contasimplesmei.mapper

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Despesa
import com.contasimplesmei.model.Usuario

fun Despesa.toResponseDTO(): DespesaResponseDTO =
    DespesaResponseDTO(
        id = this.id!!,
        categoria = this.categoria.toResponseDTO(),
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )

fun DespesaRequestDTO.toEntity(usuarioLogado: Usuario, categoria: Categoria): Despesa =
    Despesa(
        id = null,
        categoria = categoria,
        descricao = this.descricao,
        valor = this.valor,
        data = this.data,
        usuario = usuarioLogado
    )