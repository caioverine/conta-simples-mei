package com.contasimplesmei.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@Entity
@Table(name = "receitas")
data class Receita(

    @Id
    @GeneratedValue
    val id: UUID? = null,

    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    val categoria: Categoria,

    val ativo: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario
)