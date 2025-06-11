package com.contasimplesmei.service

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.model.Despesa
import com.contasimplesmei.repository.DespesaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DespesaService(
    private val repository: DespesaRepository
) {
    fun listarTodas(): List<Despesa> = repository.findAll()

    fun buscarPorId(id: UUID): Despesa? = repository.findById(id).orElse(null)

    fun criar(dto: DespesaRequestDTO): Despesa = repository.save(dto.toEntity())

    fun atualizar(id: UUID, dto: DespesaRequestDTO): Despesa? {
        return repository.findById(id).map {
            val atualizada = it.copy(
                descricao = dto.descricao,
                valor = dto.valor,
                data = dto.data,
                categoria = dto.categoria.toEntity()
            )
            repository.save(atualizada)
        }.orElse(null)
    }

    fun deletar(id: UUID) = repository.deleteById(id)
}