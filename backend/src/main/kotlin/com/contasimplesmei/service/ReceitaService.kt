package com.contasimplesmei.service

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

    fun criar(receita: Receita): Receita = repository.save(receita)

    fun atualizar(id: UUID, nova: Receita): Receita? {
        return repository.findById(id).map {
            val atualizada = it.copy(
                descricao = nova.descricao,
                valor = nova.valor,
                data = nova.data,
                categoria = nova.categoria
            )
            repository.save(atualizada)
        }.orElse(null)
    }

    fun deletar(id: UUID) = repository.deleteById(id)
}