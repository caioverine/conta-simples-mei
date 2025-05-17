package com.contasimplesmei.model

import com.contasimplesmei.enums.CategoriaReceitaEnum
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
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

    @Enumerated(EnumType.STRING)
    val categoria: CategoriaReceitaEnum

)