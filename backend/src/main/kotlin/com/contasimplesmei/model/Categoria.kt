package com.contasimplesmei.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
    val ativo: Boolean = true,
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    val usuario: Usuario,
)
