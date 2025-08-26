package com.contasimplesmei.validator

import com.contasimplesmei.enums.TipoNegocioEnum

object TipoNegocioValidator {
    /**
     * Valida se o tipo de negócio é compatível com as atividades declaradas
     */
    fun validarCompatibilidade(
        tipoNegocio: TipoNegocioEnum,
        cnaePrincipal: String,
        cnaesSecundarios: List<String> = emptyList(),
    ): List<String> {
        val erros = mutableListOf<String>()

        // Valida CNAE principal
        val tipoEsperado = TipoNegocioEnum.identificarPorCNAE(cnaePrincipal)
        if (tipoEsperado != tipoNegocio && tipoNegocio != TipoNegocioEnum.MISTO) {
            erros.add("CNAE principal $cnaePrincipal não é compatível com $tipoNegocio")
        }

        // Valida CNAEs secundários
        val tiposSecundarios = cnaesSecundarios.map { TipoNegocioEnum.identificarPorCNAE(it) }.distinct()
        if (tiposSecundarios.size > 1 && tipoNegocio != TipoNegocioEnum.MISTO) {
            erros.add("Múltiplos tipos de atividades requerem classificação MISTO")
        }

        return erros
    }

    /**
     * Sugere o tipo mais adequado baseado nas atividades
     */
    fun sugerirTipo(
        cnaePrincipal: String,
        cnaesSecundarios: List<String> = emptyList(),
    ): TipoNegocioEnum {
        val todosOsCnaes = listOf(cnaePrincipal) + cnaesSecundarios
        val tiposEncontrados = todosOsCnaes.map { TipoNegocioEnum.identificarPorCNAE(it) }.distinct()

        return when {
            tiposEncontrados.size > 1 -> TipoNegocioEnum.MISTO
            tiposEncontrados.isEmpty() -> TipoNegocioEnum.SERVICOS // Default
            else -> tiposEncontrados.first()
        }
    }
}