package com.contasimplesmei.model

import com.contasimplesmei.enums.CategoriaDespesaEnum
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
@Table(name = "despesas")
data class Despesa(

    @Id
    @GeneratedValue
    val id: UUID? =null,

    val descricao: String,
    val valor: BigDecimal,
    val data: LocalDate,

    @Enumerated(EnumType.STRING)
    val categoria: CategoriaDespesaEnum
)
