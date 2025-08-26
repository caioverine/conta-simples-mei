package com.contasimplesmei.service

import com.contasimplesmei.dto.RelatorioAnualResponseDTO
import com.contasimplesmei.dto.RelatorioMensalResponseDTO
import com.contasimplesmei.enums.FormatoRelatorioEnum
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.util.UUID

@Service
class ExportacaoRelatorioService(
    private val relatorioService: RelatorioService
) {
    fun exportarRelatorioMensal(
        usuarioId: UUID,
        mes: Int,
        ano: Int,
        formato: FormatoRelatorioEnum
    ): ByteArray {
        val relatorio = relatorioService.gerarRelatorioMensal(usuarioId, mes, ano)

        return when (formato) {
            FormatoRelatorioEnum.EXCEL -> gerarExcelMensal(relatorio)
            FormatoRelatorioEnum.PDF -> gerarPdfMensal(relatorio)
            FormatoRelatorioEnum.CSV -> gerarCsvMensal(relatorio)
        }
    }

    fun exportarRelatorioAnual(
        usuarioId: UUID,
        ano: Int,
        formato: FormatoRelatorioEnum
    ): ByteArray {
        val relatorio = relatorioService.gerarResumoAnual(usuarioId, ano)

        return when (formato) {
            FormatoRelatorioEnum.EXCEL -> gerarExcelAnual(relatorio)
            FormatoRelatorioEnum.PDF -> gerarPdfAnual(relatorio)
            FormatoRelatorioEnum.CSV -> gerarCsvAnual(relatorio)
        }
    }

    private fun gerarExcelMensal(relatorio: RelatorioMensalResponseDTO): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Relatório Mensal")

        // Cabeçalho
        var rowNum = 0
        var row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Período")
        row.createCell(1).setCellValue(relatorio.periodo)

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Tipo de Negócio")
        row.createCell(1).setCellValue(relatorio.tipoNegocio)

        // Dados Financeiros
        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Total Receitas")
        row.createCell(1).setCellValue(relatorio.resumoFinanceiro.totalReceitas.toDouble())

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Total Despesas")
        row.createCell(1).setCellValue(relatorio.resumoFinanceiro.totalDespesas.toDouble())

        // Usar ByteArrayOutputStream para gerar o arquivo
        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        return outputStream.toByteArray()
    }

    private fun gerarPdfMensal(relatorio: RelatorioMensalResponseDTO): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(outputStream)
        val pdf = PdfDocument(pdfWriter)
        val document = Document(pdf)

        // Título
        document.add(Paragraph("Relatório Mensal - ${relatorio.periodo}"))

        // Informações Gerais
        val table = Table(floatArrayOf(150f, 150f))
        table.addCell("Tipo de Negócio")
        table.addCell(relatorio.tipoNegocio)
        table.addCell("Total Receitas")
        table.addCell("R$ ${relatorio.resumoFinanceiro.totalReceitas}")
        table.addCell("Total Despesas")
        table.addCell("R$ ${relatorio.resumoFinanceiro.totalDespesas}")
        table.addCell("Total Transações")
        table.addCell("${relatorio.resumoFinanceiro.totalTransacoes}")

        document.add(table)
        document.close()

        return outputStream.toByteArray()
    }

    private fun gerarCsvMensal(relatorio: RelatorioMensalResponseDTO): ByteArray {
        val csv = StringBuilder()

        // Cabeçalho
        csv.appendLine("Período,${relatorio.periodo}")
        csv.appendLine("Tipo de Negócio,${relatorio.tipoNegocio}")
        csv.appendLine()

        // Dados Financeiros
        csv.appendLine("Total Receitas,${relatorio.resumoFinanceiro.totalReceitas}")
        csv.appendLine("Total Despesas,${relatorio.resumoFinanceiro.totalDespesas}")
        csv.appendLine("Total Transações,${relatorio.resumoFinanceiro.totalTransacoes}")

        return csv.toString().toByteArray(Charsets.UTF_8)
    }

    private fun gerarExcelAnual(relatorio: RelatorioAnualResponseDTO): ByteArray {
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Relatório Anual ${relatorio.ano}")

        var rowNum = 0
        var row = sheet.createRow(rowNum++)

        // Cabeçalho
        row.createCell(0).setCellValue("Ano")
        row.createCell(1).setCellValue(relatorio.ano.toString())

        // Resumo Anual
        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Faturamento Total")
        row.createCell(1).setCellValue(relatorio.faturamentoTotal.toDouble())

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Despesas Total")
        row.createCell(1).setCellValue(relatorio.despesasTotal.toDouble())

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Lucro Total")
        row.createCell(1).setCellValue(relatorio.lucroTotal.toDouble())

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Limite Faturamento")
        row.createCell(1).setCellValue(relatorio.limiteFaturamento.toDouble())

        row = sheet.createRow(rowNum++)
        row.createCell(0).setCellValue("Percentual Limite Usado")
        row.createCell(1).setCellValue("${relatorio.percentualLimiteUsado}%")

        val outputStream = ByteArrayOutputStream()
        workbook.write(outputStream)
        workbook.close()

        return outputStream.toByteArray()
    }

    private fun gerarPdfAnual(relatorio: RelatorioAnualResponseDTO): ByteArray {
        val outputStream = ByteArrayOutputStream()
        val pdfWriter = PdfWriter(outputStream)
        val pdf = PdfDocument(pdfWriter)
        val document = Document(pdf)

        // Título
        document.add(Paragraph("Relatório Anual - ${relatorio.ano}"))

        // Tabela com dados
        val table = Table(floatArrayOf(150f, 150f))
        table.addCell("Faturamento Total")
        table.addCell("R$ ${relatorio.faturamentoTotal}")
        table.addCell("Despesas Total")
        table.addCell("R$ ${relatorio.despesasTotal}")
        table.addCell("Lucro Total")
        table.addCell("R$ ${relatorio.lucroTotal}")
        table.addCell("Limite Faturamento")
        table.addCell("R$ ${relatorio.limiteFaturamento}")
        table.addCell("Percentual Limite Usado")
        table.addCell("${relatorio.percentualLimiteUsado}%")

        document.add(table)
        document.close()

        return outputStream.toByteArray()
    }

    private fun gerarCsvAnual(relatorio: RelatorioAnualResponseDTO): ByteArray {
        val csv = StringBuilder()

        // Cabeçalho
        csv.appendLine("Ano,${relatorio.ano}")
        csv.appendLine()

        // Dados Financeiros
        csv.appendLine("Faturamento Total,${relatorio.faturamentoTotal}")
        csv.appendLine("Despesas Total,${relatorio.despesasTotal}")
        csv.appendLine("Lucro Total,${relatorio.lucroTotal}")
        csv.appendLine("Limite Faturamento,${relatorio.limiteFaturamento}")
        csv.appendLine("Percentual Limite Usado,${relatorio.percentualLimiteUsado}%")

        return csv.toString().toByteArray(Charsets.UTF_8)
    }

}
