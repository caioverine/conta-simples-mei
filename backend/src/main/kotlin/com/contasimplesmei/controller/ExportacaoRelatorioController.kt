package com.contasimplesmei.controller

import com.contasimplesmei.enums.FormatoRelatorioEnum
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import com.contasimplesmei.service.ExportacaoRelatorioService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/relatorios/export")
class ExportacaoRelatorioController(
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val exportacaoRelatorioService: ExportacaoRelatorioService
) {
    @GetMapping("/mensal")
    fun exportarRelatorioMensal(
        @RequestParam mes: Int,
        @RequestParam ano: Int,
        @RequestParam formato: FormatoRelatorioEnum
    ): ResponseEntity<ByteArray> {
        val usuarioId = usuarioAutenticadoProvider.getIdUsuarioLogado()
        val conteudo = exportacaoRelatorioService.exportarRelatorioMensal(
            usuarioId, mes, ano, formato
        )

        val headers = HttpHeaders()
        val filename = "relatorio_${mes}_${ano}.${formato.extensao}"

        headers.contentType = when (formato) {
            FormatoRelatorioEnum.EXCEL -> MediaType.parseMediaType("application/vnd.ms-excel")
            FormatoRelatorioEnum.PDF -> MediaType.APPLICATION_PDF
            FormatoRelatorioEnum.CSV -> MediaType.parseMediaType("text/csv")
        }
        headers.setContentDispositionFormData("attachment", filename)

        return ResponseEntity.ok()
            .headers(headers)
            .body(conteudo)
    }

    @GetMapping("/anual")
    fun exportarRelatorioAnual(
        @RequestParam ano: Int,
        @RequestParam formato: FormatoRelatorioEnum
    ): ResponseEntity<ByteArray> {
        val usuarioId = usuarioAutenticadoProvider.getIdUsuarioLogado()
        val conteudo = exportacaoRelatorioService.exportarRelatorioAnual(
            usuarioId, ano, formato
        )

        val headers = HttpHeaders()
        val filename = "relatorio_anual_${ano}.${formato.extensao}"

        headers.contentType = when (formato) {
            FormatoRelatorioEnum.EXCEL -> MediaType.parseMediaType("application/vnd.ms-excel")
            FormatoRelatorioEnum.PDF -> MediaType.APPLICATION_PDF
            FormatoRelatorioEnum.CSV -> MediaType.parseMediaType("text/csv")
        }
        headers.setContentDispositionFormData("attachment", filename)

        return ResponseEntity.ok()
            .headers(headers)
            .body(conteudo)
    }
}
