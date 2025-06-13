package com.contasimplesmei.service

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Despesa
import com.contasimplesmei.repository.DespesaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class DespesaService(
    private val repository: DespesaRepository
) {
    fun listarDespesasPaginadas(page: Int, size: Int): Page<DespesaResponseDTO> {
        val pageable = PageRequest.of(page, size, Sort.by("data").descending())
        return repository.findAll(pageable).map { it.toResponseDTO() }
    }

    fun buscarPorId(id: UUID): Despesa? = repository.findById(id).orElse(null)

    fun criar(dto: DespesaRequestDTO): Despesa {
        val despesSalva = repository.save(dto.toEntity())
        val despesaCompleta = repository.findByIdWithCategoria(despesSalva.id!!)
            .orElseThrow { IllegalStateException("Despesa não encontrada após o save") }
        return despesaCompleta
    }

    fun atualizar(id: UUID, dto: DespesaRequestDTO): Despesa? {
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

    fun deletar(id: UUID) = repository.deleteById(id)
}