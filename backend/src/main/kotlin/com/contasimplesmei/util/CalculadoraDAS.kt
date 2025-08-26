package com.contasimplesmei.util

import com.contasimplesmei.enums.TipoNegocioEnum
import java.math.BigDecimal

/**
 * Utilitário para cálculos relacionados ao DAS-MEI
 */
object CalculadoraDAS {
    /**
     * Calcula o DAS com base no tipo e configurações
     */
    fun calcular(
        tipoNegocio: TipoNegocioEnum,
        temFuncionario: Boolean = false,
        ehTransportadorAutonomo: Boolean = false
    ): BigDecimal {

        // Caso especial: transportador autônomo (caminhoneiro)
        if (ehTransportadorAutonomo) {
            return BigDecimal("182.16") // 12% do salário mínimo
        }

        return TipoNegocioEnum.calcularValorDAS(tipoNegocio, temFuncionario)
    }

    /**
     * Breakdown detalhado do DAS
     */
    fun detalharDAS(
        tipoNegocio: TipoNegocioEnum,
        temFuncionario: Boolean = false
    ): Map<String, BigDecimal> {
        val detalhamento = mutableMapOf<String, BigDecimal>()

        // Base INSS (sempre presente)
        detalhamento["INSS"] = TipoNegocioEnum.VALOR_BASE_INSS

        // Impostos específicos por tipo
        when (tipoNegocio) {
            TipoNegocioEnum.COMERCIO, TipoNegocioEnum.INDUSTRIA -> {
                detalhamento["ICMS"] = BigDecimal("1.00")
            }
            TipoNegocioEnum.SERVICOS -> {
                detalhamento["ISS"] = BigDecimal("5.00")
            }
            TipoNegocioEnum.MISTO -> {
                detalhamento["ICMS"] = BigDecimal("1.00")
                detalhamento["ISS"] = BigDecimal("5.00")
            }
        }

        // Adicional por funcionário
        if (temFuncionario) {
            detalhamento["INSS_FUNCIONARIO"] = BigDecimal("45.54")
        }

        // Total
        detalhamento["TOTAL"] = detalhamento.values.sumOf { it }

        return detalhamento
    }

    /**
     * Comparação de custos entre tipos
     */
    fun compararTipos(): Map<TipoNegocioEnum, BigDecimal> {
        return TipoNegocioEnum.values().associateWith { tipo ->
            TipoNegocioEnum.calcularValorDAS(tipo, false)
        }
    }
}