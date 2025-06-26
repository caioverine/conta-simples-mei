package com.contasimplesmei.repository

import com.contasimplesmei.dto.CategoriaDespesaGroupProjection
import com.contasimplesmei.model.Despesa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.util.*

interface DespesaRepository : JpaRepository<Despesa, UUID> {
    @Query("SELECT SUM(d.valor) FROM Despesa d WHERE d.ativo = true AND d.usuario.id = :idUsuario")
    fun sumValor(
        @Param("idUsuario") idUsuario: UUID,
    ): BigDecimal?

    @Query(
        "SELECT SUM(d.valor) " +
            "FROM Despesa d " +
            "WHERE MONTH(d.data) = :mes " +
            "AND YEAR(d.data) = :ano " +
            "AND d.ativo = true " +
            "AND d.usuario.id = :idUsuario",
    )
    fun sumByMes(
        @Param("idUsuario") idUsuario: UUID,
        ano: Int,
        mes: Int,
    ): BigDecimal?

    @Query(
        "SELECT SUM(d.valor) " +
            "FROM Despesa d " +
            "WHERE MONTH(d.data) = MONTH(CURRENT_DATE) " +
            "AND YEAR(d.data) = YEAR(CURRENT_DATE) " +
            "AND d.ativo = true " +
            "AND d.usuario.id = :idUsuario",
    )
    fun sumByMesAtual(
        @Param("idUsuario") idUsuario: UUID,
    ): BigDecimal

    fun findTop5ByAtivoTrueAndUsuarioIdOrderByDataDesc(idUsuario: UUID): List<Despesa>

    @Query(
        "SELECT d.categoria as categoria, SUM(d.valor) as total " +
            "FROM Despesa d " +
            "WHERE d.ativo = true " +
            "AND d.usuario.id = :idUsuario " +
            "GROUP BY d.categoria",
    )
    fun sumGroupByCategoria(
        @Param("idUsuario") idUsuario: UUID,
    ): List<CategoriaDespesaGroupProjection>

    @Query(
        "SELECT d " +
            "FROM Despesa d " +
            "JOIN FETCH d.categoria " +
            "WHERE d.id = :id " +
            "AND d.ativo = true " +
            "AND d.usuario.id = :idUsuario",
    )
    fun findByIdWithCategoria(
        @Param("id") id: UUID,
        @Param("idUsuario") idUsuario: UUID,
    ): Optional<Despesa>

    fun findAllByAtivoTrueAndUsuarioId(
        pageable: Pageable,
        idUsuario: UUID,
    ): Page<Despesa>
}
