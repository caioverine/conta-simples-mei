package com.contasimplesmei.controller

import com.contasimplesmei.dto.ReceitaRequestDTO
import com.contasimplesmei.dto.ReceitaResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Receita
import com.contasimplesmei.service.ReceitaService
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/receitas")
class ReceitaController(
    private val service: ReceitaService
) {

    @GetMapping
    fun listarReceitas(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<ReceitaResponseDTO>> {
        val receitas = service.listarReceitasPaginadas(page, size)
        return ResponseEntity.ok(receitas)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<ReceitaResponseDTO> {
        val receita = service.buscarPorId(id)
        return if (receita != null) ResponseEntity.ok(receita.toResponseDTO())
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun criar(@RequestBody @Valid dto: ReceitaRequestDTO): ResponseEntity<ReceitaResponseDTO> {
        val receita = service.criar(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(receita.toResponseDTO())
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody dto: ReceitaRequestDTO): ResponseEntity<ReceitaResponseDTO> {
        val receita = service.atualizar(id, dto)
        return if (receita != null) ResponseEntity.ok(receita.toResponseDTO())
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}