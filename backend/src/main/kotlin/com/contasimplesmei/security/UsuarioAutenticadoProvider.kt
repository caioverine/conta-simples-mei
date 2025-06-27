package com.contasimplesmei.security

import com.contasimplesmei.model.Usuario
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UsuarioAutenticadoProvider(
    private val messageSource: MessageSource,
) {
    fun getUsuarioLogado(): Usuario {
        val authentication =
            SecurityContextHolder.getContext().authentication
                ?: throw IllegalStateException(
                    messageSource.getMessage(
                        "usuario.nao.autenticado",
                        null,
                        LocaleContextHolder.getLocale(),
                    ),
                )

        val principal = authentication.principal
        if (principal !is UsuarioPrincipal) {
            throw IllegalStateException(
                messageSource.getMessage(
                    "usuario.tipo.autenticacao.invalido",
                    null,
                    LocaleContextHolder.getLocale(),
                ),
            )
        }

        return principal.getUsuario()
    }

    fun getIdUsuarioLogado(): UUID =
        getUsuarioLogado().id
            ?: throw IllegalStateException(
                messageSource.getMessage(
                    "usuario.sem.id.valido",
                    null,
                    LocaleContextHolder.getLocale(),
                ),
            )
}
