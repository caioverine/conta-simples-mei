package com.contasimplesmei.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "despesas")
data class Despesa(

    @Id
    @GeneratedValue
    val id: UUID? =null,

    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate,

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    val categoria: Categoria,

    val ativo: Boolean = true
)
