package com.contasimplesmei.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "receitas")
data class Receita(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate,

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    val categoria: Categoria
)