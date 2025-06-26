package com.contasimplesmei.mapper

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.dto.UsuarioResponseDTO
import com.contasimplesmei.model.Usuario

fun UsuarioRequestDTO.toEntity(senhaCriptografada: String): Usuario =
    Usuario(
        id = null,
        nome = this.nome,
        email = this.email,
        senha = senhaCriptografada,
        perfil = this.perfil
    )

fun Usuario.toResponseDTO(): UsuarioResponseDTO =
    UsuarioResponseDTO(
        id = this.id!!,
        nome = this.nome,
        email = this.email,
        perfil = this.perfil
    )
