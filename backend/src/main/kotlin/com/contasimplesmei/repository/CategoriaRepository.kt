package com.contasimplesmei.repository

import com.contasimplesmei.enums.TipoCategoria
import com.contasimplesmei.model.Categoria
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaRepository : JpaRepository<Categoria, Long> {
    fun findAllByTipo(tipo: TipoCategoria): List<Categoria>
}