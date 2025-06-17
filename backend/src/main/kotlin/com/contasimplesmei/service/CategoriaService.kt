package com.contasimplesmei.service

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.repository.CategoriaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CategoriaService(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val repository: CategoriaRepository
) {

    fun listar(): List<CategoriaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        return repository.findAllByAtivoTrueAndUsuarioId(usuarioLogado.id!!).map { it.toResponseDTO() }
    }

    fun listarPorTipo(tipo: String): List<CategoriaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val tipoCategoria = runCatching {
            tipo.uppercase()
        }.getOrElse {
            throw IllegalArgumentException("Tipo de categoria inválido: $tipo. Use RECEITA ou DESPESA.")
        }

        return repository.findAllByTipoAndAtivoTrueAndUsuarioId(tipoCategoria, usuarioLogado.id!!).map { it.toResponseDTO() }
    }

    fun criar(dto: CategoriaRequestDTO): CategoriaResponseDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val categoria = Categoria(nome = dto.nome, tipo = dto.tipo, usuario = usuarioLogado)
        return repository.save(categoria).toResponseDTO()
    }

    fun deletar(id: UUID) {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val categoria = repository.findById(id)
            .orElseThrow { IllegalStateException("Categoria não encontrada para deleção") }

        if (categoria.usuario.id != usuarioLogado.id) {
            throw IllegalStateException("Categoria não pertence ao usuário logado")
        }

        if(!categoria.ativo) throw IllegalArgumentException("Categoria já inativa")

        val categoriaInativa = categoria.copy(ativo = false)
        repository.save(categoriaInativa)
    }

    fun buscarPorId(id: UUID): CategoriaResponseDTO =
        repository.findById(id).orElseThrow { RuntimeException("Categoria não encontrada") }.toResponseDTO()
}