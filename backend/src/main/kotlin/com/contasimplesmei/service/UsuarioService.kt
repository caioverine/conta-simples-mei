package com.contasimplesmei.service

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.model.Usuario
import com.contasimplesmei.repository.UsuarioRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun criar(dto: UsuarioRequestDTO): Usuario {
        val senhaCriptografada = passwordEncoder.encode(dto.senha)
        return repository.save(dto.toEntity(senhaCriptografada))
    }

    fun listar(): List<Usuario> {
        return repository.findAll()
    }

    fun buscarPorId(id: UUID): Usuario? {
        return repository.findById(id).orElse(null)
    }

    fun deletar(id: UUID) {
        repository.deleteById(id)
    }
}