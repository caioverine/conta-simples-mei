package com.contasimplesmei.service

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.exception.BusinessException
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.model.Usuario
import com.contasimplesmei.repository.UsuarioRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class UsuarioService(
    private val repository: UsuarioRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun criar(dto: UsuarioRequestDTO): Usuario {
        if (repository.existsByEmail(dto.email)) {
            throw BusinessException("E-mail já cadastrado.")
        }

        val senhaCriptografada = passwordEncoder.encode(dto.senha)
        return repository.save(dto.toEntity(senhaCriptografada))
    }

    fun listar(): List<Usuario> {
        return repository.findAll()
    }

    fun buscarPorId(id: UUID): Usuario? {
        return repository.findById(id).orElseThrow { EntityNotFoundException("Usuário não encontrado") }
    }

    @Transactional
    fun deletar(id: UUID) {
        repository.deleteById(id)
    }
}