package com.contasimplesmei.service

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Receita
import com.contasimplesmei.repository.ReceitaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ReceitaService(
    private val repository: ReceitaRepository
) {
    fun listarTodas(): List<Receita> = repository.findAll()

    fun buscarPorId(id: UUID): Receita? = repository.findById(id).orElse(null)

    fun criar(dto: ReceitaRequestDTO): Receita = repository.save(dto.toEntity())

    fun atualizar(id: UUID, dto: ReceitaRequestDTO): Receita? {
        return repository.findById(id).map {
            val atualizada = it.copy(
                descricao = dto.descricao,
                valor = dto.valor,
                data = dto.data,
                categoria = dto.categoria
            )
            repository.save(atualizada)
        }.orElse(null)
    }

    fun deletar(id: UUID) = repository.deleteById(id)
}