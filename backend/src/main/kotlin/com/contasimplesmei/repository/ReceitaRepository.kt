package com.contasimplesmei.repository

import com.contasimplesmei.model.Receita
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.util.Optional
import java.util.UUID

interface ReceitaRepository : JpaRepository<Receita, UUID> {
    @Query("SELECT SUM(r.valor) FROM Receita r WHERE r.ativo = true")
    fun sumValor(): BigDecimal?

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE MONTH(r.data) = :mes AND YEAR(r.data) = :ano AND r.ativo = true")
    fun sumByMes(ano: Int, mes: Int): BigDecimal?

    @Query("SELECT SUM(r.valor) FROM Receita r WHERE MONTH(r.data) = MONTH(CURRENT_DATE) AND YEAR(r.data) = YEAR(CURRENT_DATE) AND r.ativo = true")
    fun sumByMesAtual(): BigDecimal

    fun findTop5ByAtivoTrueOrderByDataDesc(): List<Receita>

    @Query("SELECT r FROM Receita r JOIN FETCH r.categoria WHERE r.id = :id AND r.ativo = true")
    fun findByIdWithCategoria(@Param("id") id: UUID): Optional<Receita>

    fun findAllByAtivoTrue(pageable: Pageable): Page<Receita>
}