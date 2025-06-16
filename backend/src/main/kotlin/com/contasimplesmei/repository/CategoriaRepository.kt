package com.contasimplesmei.repository

import com.contasimplesmei.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CategoriaRepository : JpaRepository<Categoria, UUID> {
    fun findAllByTipoAndAtivoTrue(tipo: String): List<Categoria>

    fun findAllByAtivoTrue(): List<Categoria>
}