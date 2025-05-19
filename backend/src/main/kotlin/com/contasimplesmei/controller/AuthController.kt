package com.contasimplesmei.controller

import com.contasimplesmei.dto.AuthRequestDTO
import com.contasimplesmei.dto.AuthResponseDTO
import com.contasimplesmei.repository.UsuarioRepository
import com.contasimplesmei.security.JwtTokenProvider
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenProvider: JwtTokenProvider,
    private val usuarioRepository: UsuarioRepository
) {

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequestDTO): ResponseEntity<AuthResponseDTO> {
        val authenticationToken = UsernamePasswordAuthenticationToken(request.email, request.senha)
        authenticationManager.authenticate(authenticationToken)

        val usuario = usuarioRepository.findByEmail(request.email)
            ?: return ResponseEntity.badRequest().build()

        val token = jwtTokenProvider.gerarToken(usuario.email)

        return ResponseEntity.ok(
            AuthResponseDTO(
                token = token,
                nome = usuario.nome,
                email = usuario.email
            )
        )
    }
}