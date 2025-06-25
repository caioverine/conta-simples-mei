package com.contasimplesmei.integration

import com.contasimplesmei.ContaSimplesMeiApplication
import com.contasimplesmei.repository.UsuarioRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import kotlin.test.assertEquals

@SpringBootTest(classes = [ContaSimplesMeiApplication::class])
@AutoConfigureMockMvc
class UsuarioIntegrationTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var repository: UsuarioRepository

    @BeforeEach
    fun setUp() {
        repository.deleteAll()
    }

    @Test
    fun `deve criar usuario com sucesso via endpoint POST usuarios`() {
        val novoUsuario = mapOf(
            "nome" to "Maria",
            "email" to "maria@email.com",
            "senha" to "SenhaSegura456"
        )

        mockMvc.post("/usuarios") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(novoUsuario)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.nome") { value("Maria") }
            jsonPath("$.email") { value("maria@email.com") }
        }

        // Verifica no banco
        val usuarios = repository.findAll()
        assertEquals(1, usuarios.size)
        assertEquals("maria@email.com", usuarios[0].email)
    }
}