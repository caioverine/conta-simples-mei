package com.contasimplesmei.service

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val repository: CategoriaRepository
) {

    fun listar(): List<CategoriaResponseDTO> =
        repository.findAll().map { it.toResponseDTO() }

    fun listarPorTipo(tipo: String): List<CategoriaResponseDTO> {
        val tipoCategoria = runCatching {
            tipo.uppercase()
        }.getOrElse {
            throw IllegalArgumentException("Tipo de categoria inválido: $tipo. Use RECEITA ou DESPESA.")
        }

        return repository.findAllByTipo(tipoCategoria).map { it.toResponseDTO() }
    }

    fun criar(dto: CategoriaRequestDTO): CategoriaResponseDTO {
        val categoria = Categoria(nome = dto.nome, tipo = dto.tipo)
        return repository.save(categoria).toResponseDTO()
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
    }

    fun buscarPorId(id: Long): CategoriaResponseDTO =
        repository.findById(id).orElseThrow { RuntimeException("Categoria não encontrada") }.toResponseDTO()
}