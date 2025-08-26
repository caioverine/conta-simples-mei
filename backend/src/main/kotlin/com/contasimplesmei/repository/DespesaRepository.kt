package com.contasimplesmei.repository

import com.contasimplesmei.dto.CategoriaDespesaGroupProjection
import com.contasimplesmei.dto.ResumoMensalDespesaDTO
import com.contasimplesmei.model.Despesa
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Optional
import java.util.UUID

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

    /**
     * Busca despesas por usuário e período
     */
    @Query("""
        SELECT d FROM Despesa d 
        WHERE d.usuario.id = :usuarioId 
        AND d.data BETWEEN :dataInicio AND :dataFim
        ORDER BY d.data DESC
    """)
    fun findByUsuarioIdAndDataBetween(
        @Param("usuarioId") usuarioId: UUID,
        @Param("dataInicio") dataInicio: LocalDate,
        @Param("dataFim") dataFim: LocalDate,
    ): List<Despesa>

    /**
     * Calcula total de despesas por período
     */
    @Query("""
        SELECT COALESCE(SUM(d.valor), 0) 
        FROM Despesa d 
        WHERE d.usuario.id = :usuarioId 
        AND d.data BETWEEN :dataInicio AND :dataFim
    """)
    fun calcularTotalDespesasPorPeriodo(
        @Param("usuarioId") usuarioId: UUID,
        @Param("dataInicio") dataInicio: LocalDate,
        @Param("dataFim") dataFim: LocalDate,
    ): BigDecimal

    /**
     * Resumo mensal de despesas
     */
    @Query("""
        SELECT new com.contasimplesmei.dto.ResumoMensalDespesaDTO(
            EXTRACT(MONTH FROM d.data),
            EXTRACT(YEAR FROM d.data),
            COALESCE(SUM(d.valor), 0),
            COUNT(d)
        )
        FROM Despesa d 
        WHERE d.usuario.id = :usuarioId 
        AND d.data >= :dataInicio
        GROUP BY EXTRACT(YEAR FROM d.data), EXTRACT(MONTH FROM d.data)
        ORDER BY EXTRACT(YEAR FROM d.data) DESC, EXTRACT(MONTH FROM d.data) DESC
    """)
    fun obterResumoMensalDespesas(
        @Param("usuarioId") usuarioId: UUID,
        @Param("dataInicio") dataInicio: LocalDate,
    ): List<ResumoMensalDespesaDTO>
}
