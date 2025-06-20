package com.contasimplesmei.service

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.dto.ReceitaResponseDTO
import com.contasimplesmei.exception.BusinessException
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Receita
import com.contasimplesmei.repository.CategoriaRepository
import com.contasimplesmei.repository.ReceitaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ReceitaService(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val repository: ReceitaRepository,
    private val categoriaRepository: CategoriaRepository
) {
    fun listarReceitasPaginadas(page: Int, size: Int): Page<ReceitaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val pageable = PageRequest.of(page, size, Sort.by("data").descending())
        return repository.findAllByAtivoTrueAndUsuarioId(pageable, usuarioLogado.id!!).map {it.toResponseDTO()}
    }

    fun buscarPorId(id: UUID): ReceitaResponseDTO? =
        repository.findById(id).orElseThrow { EntityNotFoundException("Receita não encontrada") }.toResponseDTO()

    @Transactional
    fun criar(dto: ReceitaRequestDTO): ReceitaResponseDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)

        val receitaSalva = repository.save(dto.toEntity(usuarioLogado, categoria))
        val receitaCompleta = repository.findByIdWithCategoria(receitaSalva.id!!, usuarioLogado.id!!)
            .orElseThrow { EntityNotFoundException("Receita não encontrada após o save") }
        return receitaCompleta.toResponseDTO()
    }

    @Transactional
    fun atualizar(id: UUID, dto: ReceitaRequestDTO): ReceitaResponseDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val receita = repository.findById(id)
            .orElse(null)
            ?.takeIf { it.usuario.id == usuarioLogado.id }
            ?: throw EntityNotFoundException("Receita não encontrada ou não pertence ao usuário logado")

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)

        val receitaAtualizada = receita.copy(
            descricao = dto.descricao,
            valor = dto.valor,
            data = dto.data,
            categoria = categoria
        )

        return repository.save(receitaAtualizada).toResponseDTO()
    }

    @Transactional
    fun deletar(id: UUID) {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val receita = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Receita não encontrada para deleção") }

        if (receita.usuario.id != usuarioLogado.id) {
            throw BusinessException("Receita não pertence ao usuário logado")
        }

        if(!receita.ativo) throw IllegalStateException("Receita já inativa")

        val receitaInativa = receita.copy(ativo = false)
        repository.save(receitaInativa)
    }
}