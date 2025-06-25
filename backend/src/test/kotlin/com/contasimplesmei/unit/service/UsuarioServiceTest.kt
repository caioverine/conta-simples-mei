package com.contasimplesmei.unit.service

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.enums.PerfilUsuarioEnum
import com.contasimplesmei.model.Usuario
import com.contasimplesmei.repository.UsuarioRepository
import com.contasimplesmei.service.UsuarioService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.UUID
import kotlin.test.assertEquals

@ExtendWith(MockitoExtension::class)
class UsuarioServiceTest {

    @Mock
    private lateinit var repository: UsuarioRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var usuarioService: UsuarioService

    @Test
    fun `deve criar usuario com senha criptografada`() {
        // Arrange
        val requestDTO = UsuarioRequestDTO(
            nome = "João",
            email = "joao@email.com",
            senha = "SenhaForte123",
            perfil = PerfilUsuarioEnum.USER
        )

        val senhaCriptografada = "criptografada123"

        val usuarioSalvo = Usuario(
            id = UUID.randomUUID(),
            nome = "João",
            email = "joao@email.com",
            senha = senhaCriptografada,
            perfil = PerfilUsuarioEnum.USER
        )

        whenever(passwordEncoder.encode(requestDTO.senha)).thenReturn(senhaCriptografada)
        whenever(repository.save(any())).thenReturn(usuarioSalvo)

        // Act
        val result = usuarioService.criar(requestDTO)

        // Assert
        assertEquals("João", result.nome)
        assertEquals("joao@email.com", result.email)
        verify(passwordEncoder).encode("SenhaForte123")
        verify(repository).save(any())
    }
}
