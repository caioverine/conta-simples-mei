package com.contasimplesmei.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "categorias")
data class Categoria(
    @Id
    @GeneratedValue
    val id: UUID? = null,

    @Column(nullable = false)
    val nome: String? = null,

    @Column(nullable = false)
    val tipo: String? = null,

    val ativo: Boolean = true
)
