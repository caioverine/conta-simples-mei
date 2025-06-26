package com.contasimplesmei.security

import com.contasimplesmei.model.Usuario
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UsuarioAutenticadoProvider {
    fun getUsuarioLogado(): Usuario {
        val authentication =
            SecurityContextHolder.getContext().authentication
                ?: throw IllegalStateException("Usuário não está autenticado")

        val principal = authentication.principal
        if (principal !is UsuarioPrincipal) {
            throw IllegalStateException("Tipo de autenticação inválido")
        }

        return principal.getUsuario()
    }

    fun getIdUsuarioLogado(): UUID = getUsuarioLogado().id ?: throw IllegalStateException("Usuário não possui ID válido")
}
