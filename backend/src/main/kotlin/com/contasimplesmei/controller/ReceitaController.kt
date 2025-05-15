package com.contasimplesmei.controller

import com.contasimplesmei.model.Receita
import com.contasimplesmei.service.ReceitaService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.UUID

@RestController
@RequestMapping("/receitas")
class ReceitaController(
    private val service: ReceitaService
) {

    @GetMapping
    fun listar(): ResponseEntity<List<Receita>> = ResponseEntity.ok(service.listarTodas())

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: UUID): ResponseEntity<Receita> {
        val receita = service.buscarPorId(id)
        return if (receita != null) ResponseEntity.ok(receita)
        else ResponseEntity.notFound().build()
    }

    @PostMapping
    fun criar(@RequestBody receita: Receita): ResponseEntity<Receita> {
        val nova = service.criar(receita)
        return ResponseEntity.created(URI.create("/receitas/${nova.id}")).body(nova)
    }

    @PutMapping("/{id}")
    fun atualizar(@PathVariable id: UUID, @RequestBody receita: Receita): ResponseEntity<Receita> {
        val atualizada = service.atualizar(id, receita)
        return if (atualizada != null) ResponseEntity.ok(atualizada)
        else ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deletar(@PathVariable id: UUID): ResponseEntity<Void> {
        service.deletar(id)
        return ResponseEntity.noContent().build()
    }
}