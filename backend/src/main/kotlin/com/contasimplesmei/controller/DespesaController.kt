package com.contasimplesmei.controller

import com.contasimplesmei.dto.DespesaRequestDTO
import com.contasimplesmei.dto.DespesaResponseDTO
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.service.DespesaService
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
import java.util.UUID

@RestController
@RequestMapping("/despesas")
class DespesaController(
    private val service: DespesaService
) {
    @GetMapping
    fun listarDespesas(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<DespesaResponseDTO>> {
        val despesas = service.listarDespesasPaginadas(page, size)
        return ResponseEntity.ok(despesas)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<DespesaResponseDTO> {
        val despesa = service.buscarPorId(id)
        return if (despesa != null) ResponseEntity.ok(despesa)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun criar(@RequestBody @Valid dto: DespesaRequestDTO): ResponseEntity<DespesaResponseDTO> {
        val despesa = service.criar(dto)
        return ResponseEntity.status(HttpStatus.CREATED).body(despesa)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody dto: DespesaRequestDTO): ResponseEntity<DespesaResponseDTO> {
        val despesa = service.atualizar(id, dto)
        return if (despesa != null) ResponseEntity.ok(despesa)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}