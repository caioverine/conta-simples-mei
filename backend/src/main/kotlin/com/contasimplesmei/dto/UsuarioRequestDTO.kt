package com.contasimplesmei.dto

import com.contasimplesmei.anotation.SenhaForte
import com.contasimplesmei.enums.PerfilUsuarioEnum
import jakarta.validation.constraints.Email

data class UsuarioRequestDTO(
    val nome: String,

    @field:Email(message = "E-mail inválido")
    val email: String,

    @field:SenhaForte
    val senha: String,
    val perfil: PerfilUsuarioEnum
)
