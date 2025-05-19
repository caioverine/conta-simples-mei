package com.contasimplesmei.model

import com.contasimplesmei.enums.PerfilUsuarioEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "usuarios")
data class Usuario(
    @Id
    val id: UUID? = null,

    @Column(nullable = false)
    val nome: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val senha: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val perfil: PerfilUsuarioEnum
)
