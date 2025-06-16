package com.contasimplesmei.service

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.dto.ReceitaResponseDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Receita
import com.contasimplesmei.repository.ReceitaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReceitaService(
    private val repository: ReceitaRepository
) {
    fun listarReceitasPaginadas(page: Int, size: Int): Page<ReceitaResponseDTO> {
        val pageable = PageRequest.of(page, size, Sort.by("data").descending())
        return repository.findAllByAtivoTrue(pageable).map {it.toResponseDTO()}
    }

    fun buscarPorId(id: UUID): Receita? = repository.findById(id).orElse(null)

    fun criar(dto: ReceitaRequestDTO): Receita {
        val receitaSalva = repository.save(dto.toEntity())
        val receitaCompleta = repository.findByIdWithCategoria(receitaSalva.id!!)
            .orElseThrow { IllegalStateException("Receita não encontrada após o save") }
        return receitaCompleta
    }

    fun atualizar(id: UUID, dto: ReceitaRequestDTO): Receita? {
        return repository.findById(id).map {
            val atualizada = it.copy(
                descricao = dto.descricao,
                valor = dto.valor,
                data = dto.data,
                categoria = Categoria(id = dto.idCategoria)
            )
            repository.save(atualizada)
        }.orElse(null)
    }

    fun deletar(id: UUID) {
        val receita = repository.findById(id)
            .orElseThrow { IllegalStateException("Receita não encontrada para deleção") }

        if(!receita.ativo) throw IllegalStateException("Receita já inativa")

        val receitaInativa = receita.copy(ativo = false)
        repository.save(receitaInativa)
    }
}