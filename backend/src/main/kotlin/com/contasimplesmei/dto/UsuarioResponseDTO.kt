package com.contasimplesmei.dto

import com.contasimplesmei.enums.PerfilUsuarioEnum
import java.util.UUID

data class UsuarioResponseDTO(
    val id: UUID,
    val nome: String,
    val email: String,
    val perfil: PerfilUsuarioEnum,
)
