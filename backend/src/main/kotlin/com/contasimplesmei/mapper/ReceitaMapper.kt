package com.contasimplesmei.mapper

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.dto.ReceitaResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Receita
import com.contasimplesmei.model.Usuario
import java.util.UUID

fun Receita.toResponseDTO(): ReceitaResponseDTO =
    ReceitaResponseDTO(
        id = this.id!!,
        categoria = this.categoria.toResponseDTO(),
        descricao = this.descricao,
        valor = this.valor,
        data = this.data
    )

fun ReceitaRequestDTO.toEntity(usuarioLogado: Usuario, categoria: Categoria): Receita =
    Receita(
        id = null,
        categoria = categoria,
        descricao = this.descricao,
        valor = this.valor,
        data = this.data,
        usuario = usuarioLogado
    )
