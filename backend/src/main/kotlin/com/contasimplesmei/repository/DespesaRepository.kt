package com.contasimplesmei.repository

import com.contasimplesmei.dto.CategoriaDespesaGroupProjection
import com.contasimplesmei.model.Despesa
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal
import java.util.UUID

interface DespesaRepository : JpaRepository<Despesa, UUID> {
    @Query("SELECT SUM(d.valor) FROM Despesa d")
    fun sumValor(): BigDecimal?

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE MONTH(d.data) = :mes AND YEAR(d.data) = :ano")
    fun sumByMes(ano: Int, mes: Int): BigDecimal?

    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE MONTH(d.data) = MONTH(CURRENT_DATE) AND YEAR(d.data) = YEAR(CURRENT_DATE)")
    fun sumByMesAtual(): BigDecimal

    fun findTop5ByOrderByDataDesc(): List<Despesa>

    @Query("SELECT d.categoria as categoria, SUM(d.valor) as total FROM Despesa d GROUP BY d.categoria")
    fun sumGroupByCategoria(): List<CategoriaDespesaGroupProjection>
}