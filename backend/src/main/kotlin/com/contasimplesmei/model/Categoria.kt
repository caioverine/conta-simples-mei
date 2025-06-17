package com.contasimplesmei.model

import jakarta.persistence.*
import java.util.*

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

    val ativo: Boolean = true,

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario
)
