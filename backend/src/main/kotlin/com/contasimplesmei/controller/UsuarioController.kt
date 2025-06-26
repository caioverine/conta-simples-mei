package com.contasimplesmei.controller

import com.contasimplesmei.dto.UsuarioRequestDTO
import com.contasimplesmei.dto.UsuarioResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.service.CategoriaService
import com.contasimplesmei.service.UsuarioService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/usuarios")
class UsuarioController(
    private val service: UsuarioService,
    private val categoriaService: CategoriaService,
) {
    @PostMapping
    fun criar(
        @RequestBody @Valid dto: UsuarioRequestDTO,
    ): ResponseEntity<UsuarioResponseDTO> {
        val usuario = service.criar(dto)
        categoriaService.criarCategoriasPadraoParaUsuario(usuario)
        return ResponseEntity.status(HttpStatus.CREATED).body(usuario.toResponseDTO())
    }

    @GetMapping
    fun listar(): ResponseEntity<List<UsuarioResponseDTO>> {
        val usuarios = service.listar().map { it.toResponseDTO() }
        return ResponseEntity.ok(usuarios)
    }

    @GetMapping("/{id}")
    fun buscarPorId(
        @PathVariable id: UUID,
    ): ResponseEntity<UsuarioResponseDTO> {
        val usuario = service.buscarPorId(id)
        return if (usuario != null) {
            ResponseEntity.ok(usuario.toResponseDTO())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deletar(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
