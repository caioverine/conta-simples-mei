package com.contasimplesmei.service

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Despesa
import com.contasimplesmei.repository.CategoriaRepository
import com.contasimplesmei.repository.DespesaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class DespesaService(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val repository: DespesaRepository,
    private val categoriaRepository: CategoriaRepository
) {
    fun listarDespesasPaginadas(page: Int, size: Int): Page<DespesaResponseDTO> {
        val pageable = PageRequest.of(page, size, Sort.by("data").descending())
        return repository.findAllByAtivoTrue(pageable).map { it.toResponseDTO() }
    }

    fun buscarPorId(id: UUID): DespesaResponseDTO? = repository.findById(id).orElse(null).toResponseDTO()

    fun criar(dto: DespesaRequestDTO): DespesaResponseDTO {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)
            ?: throw IllegalStateException("Categoria Inválida ou não pertence ao usuário logado")

        val despesSalva = repository.save(dto.toEntity(usuarioLogado, categoria))
        val despesaCompleta = repository.findByIdWithCategoria(despesSalva.id!!)
            .orElseThrow { IllegalStateException("Despesa não encontrada após o save") }
        return despesaCompleta.toResponseDTO()
    }

    fun atualizar(id: UUID, dto: DespesaRequestDTO): DespesaResponseDTO? {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val despesa = repository.findById(id)
            .orElse(null)
            ?.takeIf { it.usuario?.id == usuarioLogado.id }
            ?: throw IllegalStateException("Despesa não encontrada ou não pertence ao usuário logado")

        val categoria = categoriaRepository.findByIdAndUsuarioId(dto.idCategoria, usuarioLogado.id!!)
            ?: throw IllegalStateException("Categoria Inválida ou não pertence ao usuário logado")

        val despesaAtualizada = despesa.copy(
            descricao = dto.descricao,
            valor = dto.valor,
            data = dto.data,
            categoria = categoria
        )

        return repository.save(despesaAtualizada).toResponseDTO()
    }

    fun deletar(id: UUID) {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val despesa = repository.findById(id)
            .orElseThrow { IllegalStateException("Despesa não encontrada para deleção") }

        if (despesa.usuario?.id != usuarioLogado.id) {
            throw IllegalStateException("Despesa não pertence ao usuário logado")
        }

        if (!despesa.ativo) {
            throw IllegalStateException("Despesa já está inativa")
        }

        val despesaInativa = despesa.copy(ativo = false)
        repository.save(despesaInativa)
    }
}