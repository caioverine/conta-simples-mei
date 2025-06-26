package com.contasimplesmei.repository

import com.contasimplesmei.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoriaRepository : JpaRepository<Categoria, UUID> {
    fun findAllByTipoAndAtivoTrueAndUsuarioId(tipo: String, idUsuario: UUID): List<Categoria>

    fun findAllByAtivoTrueAndUsuarioId(idUsuario: UUID): List<Categoria>

    fun findByIdAndUsuarioId(id: UUID, idUsuario: UUID): Categoria
}
