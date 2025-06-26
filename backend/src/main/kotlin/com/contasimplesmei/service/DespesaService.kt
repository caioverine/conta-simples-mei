package com.contasimplesmei.service

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.exception.BusinessException
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.repository.CategoriaRepository
import com.contasimplesmei.repository.DespesaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DespesaService(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val repository: DespesaRepository,
    private val categoriaRepository: CategoriaRepository
) {
    fun listarDespesasPaginadas(page: Int, size: Int): Page<DespesaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val pageable = PageRequest.of(page, size, Sort.by("data").descending())
        return repository.findAllByAtivoTrueAndUsuarioId(pageable, usuarioLogado.id!!).map { it.toResponseDTO() }
    }

    fun buscarPorId(id: UUID): DespesaResponseDTO? =
        repository.findById(id).orElseThrow { EntityNotFoundException("Despesa não encontrada") }.toResponseDTO()

    @Transactional
    fun criar(dto: DespesaRequestDTO): DespesaResponseDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)

        val despesSalva = repository.save(dto.toEntity(usuarioLogado, categoria))
        val despesaCompleta = repository.findByIdWithCategoria(despesSalva.id!!, usuarioLogado.id!!)
            .orElseThrow { EntityNotFoundException("Despesa não encontrada após o save") }
        return despesaCompleta.toResponseDTO()
    }

    @Transactional
    fun atualizar(id: UUID, dto: DespesaRequestDTO): DespesaResponseDTO? {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val despesa = repository.findById(id)
            .orElse(null)
            ?.takeIf { it.usuario.id == usuarioLogado.id }
            ?: throw EntityNotFoundException("Despesa não encontrada ou não pertence ao usuário logado")

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)

        val despesaAtualizada = despesa.copy(
            descricao = dto.descricao,
            valor = dto.valor,
            data = dto.data,
            categoria = categoria
        )

        return repository.save(despesaAtualizada).toResponseDTO()
    }

    @Transactional
    fun deletar(id: UUID) {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val despesa = repository.findById(id)
            .orElseThrow { EntityNotFoundException("Despesa não encontrada para deleção") }

        if (despesa.usuario.id != usuarioLogado.id) {
            throw BusinessException("Despesa não pertence ao usuário logado")
        }

        if (!despesa.ativo) {
            throw IllegalStateException("Despesa já está inativa")
        }

        val despesaInativa = despesa.copy(ativo = false)
        repository.save(despesaInativa)
    }
}
