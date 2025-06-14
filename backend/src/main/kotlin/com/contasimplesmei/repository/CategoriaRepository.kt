package com.contasimplesmei.repository

import com.contasimplesmei.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun findAllByTipo(tipo: String): List<Categoria>
}