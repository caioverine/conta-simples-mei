package com.contasimplesmei.controller

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.service.CategoriaService
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
@RequestMapping("/categorias")
class CategoriaController(
    private val service: CategoriaService
) {

    @GetMapping
    fun listarTodas(): ResponseEntity<List<CategoriaResponseDTO>> =
        ResponseEntity.ok(service.listar())

    @GetMapping("/tipo/{tipo}")
    fun listarPorTipo(@PathVariable tipo: String): ResponseEntity<List<CategoriaResponseDTO>> =
        ResponseEntity.ok(service.listarPorTipo(tipo))

    @PostMapping
    fun criar(@RequestBody dto: CategoriaRequestDTO): ResponseEntity<CategoriaResponseDTO> =
        ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto))

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<CategoriaResponseDTO> =
        ResponseEntity.ok(service.buscarPorId(id))

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}
