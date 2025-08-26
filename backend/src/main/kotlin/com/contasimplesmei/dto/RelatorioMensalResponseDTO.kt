package com.contasimplesmei.dto

import java.time.LocalDateTime

data class RelatorioMensalResponseDTO(
    val mes: Int,
    val ano: Int,
    val periodo: String,
    val tipoNegocio: String,
    val resumoFinanceiro: ResumoFinanceiroDTO,
    // TODO
//    val das: InfosDasDTO,
    val calculadoEm: LocalDateTime,
)
