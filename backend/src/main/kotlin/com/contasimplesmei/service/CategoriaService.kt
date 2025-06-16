package com.contasimplesmei.service

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.repository.CategoriaRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CategoriaService(
    private val repository: CategoriaRepository
) {

    fun listar(): List<CategoriaResponseDTO> =
        repository.findAllByAtivoTrue().map { it.toResponseDTO() }

    fun listarPorTipo(tipo: String): List<CategoriaResponseDTO> {
        val tipoCategoria = runCatching {
            tipo.uppercase()
        }.getOrElse {
            throw IllegalArgumentException("Tipo de categoria inválido: $tipo. Use RECEITA ou DESPESA.")
        }

        return repository.findAllByTipoAndAtivoTrue(tipoCategoria).map { it.toResponseDTO() }
    }

    fun criar(dto: CategoriaRequestDTO): CategoriaResponseDTO {
        val categoria = Categoria(nome = dto.nome, tipo = dto.tipo)
        return repository.save(categoria).toResponseDTO()
    }

    fun deletar(id: UUID) {
        val categoria = repository.findById(id)
            .orElseThrow { IllegalStateException("Despesa não encontrada para deleção") }

        if(!categoria.ativo) throw IllegalArgumentException("Categoria já inativa")

        val categoriaInativa = categoria.copy(ativo = false)
        repository.save(categoriaInativa)
    }

    fun buscarPorId(id: UUID): CategoriaResponseDTO =
        repository.findById(id).orElseThrow { RuntimeException("Categoria não encontrada") }.toResponseDTO()
}