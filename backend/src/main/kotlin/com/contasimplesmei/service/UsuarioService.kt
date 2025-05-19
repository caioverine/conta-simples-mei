package com.contasimplesmei.service

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.mapper.toEntity
import com.contasimplesmei.model.Usuario
import com.contasimplesmei.repository.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UsuarioService(
    private val repository: UsuarioRepository
) {

    fun criar(dto: UsuarioRequestDTO): Usuario {
        // TODO criptografia de senha
        return repository.save(dto.toEntity())
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