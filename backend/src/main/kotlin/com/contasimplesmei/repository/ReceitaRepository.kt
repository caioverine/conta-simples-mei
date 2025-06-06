package com.contasimplesmei.repository

import com.contasimplesmei.model.Receita
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal
import java.util.UUID

interface ReceitaRepository : JpaRepository<Receita, UUID> {
    @Query("SELECT SUM(r.valor) FROM Receita r")
    fun sumValor(): BigDecimal?

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE MONTH(r.data) = :mes AND YEAR(r.data) = :ano")
    fun sumByMes(ano: Int, mes: Int): BigDecimal?

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE MONTH(r.data) = MONTH(CURRENT_DATE) AND YEAR(r.data) = YEAR(CURRENT_DATE)")
    fun sumByMesAtual(): BigDecimal

    fun findTop5ByOrderByDataDesc(): List<Receita>
}