package com.contasimplesmei.dto

import com.contasimplesmei.enums.PerfilUsuarioEnum

data class UsuarioRequestDTO(
    val nome: String,
    val email: String,
    val senha: String,
    val perfil: PerfilUsuarioEnum
)
