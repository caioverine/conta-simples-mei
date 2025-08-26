package com.contasimplesmei.enums

import java.math.BigDecimal

enum class TipoNegocioEnum(
    val descricao: String,
    val impostoAdicional: String,
    val valorAdicional: BigDecimal,
    val exemplosAtividades: List<String>,
) {
    /**
     * COMÉRCIO - Atividades de compra e venda de mercadorias
     * Valor DAS 2025: R$ 75,90 (INSS) + R$ 1,00 (ICMS) = R$ 76,90
     */
    COMERCIO(
        descricao = "Comércio de mercadorias",
        impostoAdicional = "ICMS",
        valorAdicional = BigDecimal("1.00"),
        exemplosAtividades = listOf(
            "Comércio varejista de roupas e acessórios",
            "Comércio de produtos alimentícios",
            "Comércio de materiais de construção",
            "Comércio de produtos eletrônicos",
            "Comércio de produtos de beleza",
            "Comércio de livros e revistas",
            "Comércio de produtos infantis",
            "Padaria e confeitaria (venda de produtos)",
            "Farmácia de manipulação (venda de medicamentos)",
            "Comércio de flores e plantas ornamentais",
        ),
    ),

    /**
     * INDÚSTRIA - Atividades de transformação/fabricação de produtos
     * Valor DAS 2025: R$ 75,90 (INSS) + R$ 1,00 (ICMS) = R$ 76,90
     */
    INDUSTRIA(
        descricao = "Indústria e fabricação",
        impostoAdicional = "ICMS",
        valorAdicional = BigDecimal("1.00"),
        exemplosAtividades = listOf(
            "Fabricação de produtos de panificação",
            "Fabricação de produtos têxteis",
            "Fabricação de móveis de madeira",
            "Fabricação de produtos de limpeza",
            "Fabricação de cosméticos",
            "Fabricação de produtos alimentícios",
            "Fabricação de produtos de couro",
            "Fabricação de objetos de decoração",
            "Fabricação de produtos de metal",
            "Confecção de roupas e acessórios",
        ),
    ),

    /**
     * SERVICOS - Atividades de prestação de serviços
     * Valor DAS 2025: R$ 75,90 (INSS) + R$ 5,00 (ISS) = R$ 80,90
     */
    SERVICOS(
        descricao = "Prestação de serviços",
        impostoAdicional = "ISS",
        valorAdicional = BigDecimal("5.00"),
        exemplosAtividades = listOf(
            "Cabeleireiro e outros serviços de beleza",
            "Serviços de manutenção e reparação",
            "Serviços de limpeza domiciliar",
            "Consultoria em tecnologia da informação",
            "Serviços de fotografia",
            "Serviços de design gráfico",
            "Serviços de consultoria empresarial",
            "Serviços de treinamento e ensino",
            "Serviços de marketing digital",
            "Serviços de contabilidade (técnico)",
        ),
    ),

    /**
     * MISTO - Atividades que combinam comércio E serviços
     * Valor DAS 2025: R$ 75,90 (INSS) + R$ 1,00 (ICMS) + R$ 5,00 (ISS) = R$ 81,90
     */
    MISTO(
        descricao = "Comércio e serviços (atividade mista)",
        impostoAdicional = "ICMS + ISS",
        valorAdicional = BigDecimal("6.00"),
        exemplosAtividades = listOf(
            "Restaurante (vende comida + presta serviço)",
            "Oficina mecânica (vende peças + presta serviço)",
            "Salão de beleza (vende produtos + presta serviço)",
            "Pet shop (vende produtos + serviços veterinários)",
            "Loja de informática (vende equipamentos + manutenção)",
            "Loja de bicicletas (vende + conserta)",
            "Farmácia (vende medicamentos + aplicação)",
            "Ótica (vende óculos + exames)",
            "Academia (vende produtos + personal trainer)",
            "Lanchonete (vende alimentos + serviço de alimentação)",
        ),
    );

    companion object {
        // Valor base INSS para todos os tipos (5% do salário mínimo 2025)
        val VALOR_BASE_INSS = BigDecimal("75.90")

        /**
         * Calcula o valor total do DAS para o tipo de negócio
         */
        fun calcularValorDAS(tipo: TipoNegocioEnum, temFuncionario: Boolean = false): BigDecimal {
            var valorTotal = VALOR_BASE_INSS + tipo.valorAdicional

            // Adicional se tiver funcionário (mais 3% do salário mínimo)
            if (temFuncionario) {
                valorTotal = valorTotal.add(BigDecimal("45.54")) // 3% de R$ 1.518,00
            }

            return valorTotal
        }

        /**
         * Identifica o tipo baseado na atividade principal (CNAE)
         */
        fun identificarPorCNAE(cnae: String): TipoNegocioEnum {
            return when {
                cnae.startsWith("47") -> COMERCIO // Comércio varejista
                cnae.startsWith("46") -> COMERCIO // Comércio atacadista
                cnae.startsWith("10") || cnae.startsWith("11") ||
                        cnae.startsWith("13") || cnae.startsWith("14") -> INDUSTRIA // Fabricação
                cnae.startsWith("96") -> SERVICOS // Serviços pessoais
                cnae.startsWith("95") -> SERVICOS // Reparação
                cnae.startsWith("56") -> MISTO // Alimentação (restaurantes)
                else -> SERVICOS // Default para serviços
            }
        }

        /**
         * Lista de CNAEs mais comuns por tipo
         */
        val CNAES_POR_TIPO = mapOf(
            COMERCIO to listOf(
                "4751-2/01", // Comércio varejista especializado de equipamentos de telefonia e comunicação
                "4753-9/00", // Comércio varejista especializado de eletrodomésticos e equipamentos de áudio e vídeo
                "4759-8/01", // Comércio varejista de móveis
                "4781-4/00", // Comércio varejista de artigos do vestuário e acessórios
                "4789-0/05", // Comércio varejista de produtos alimentícios em geral
            ),
            INDUSTRIA to listOf(
                "1091-1/01", // Fabricação de produtos de padaria e confeitaria com predominância de produção própria
                "1412-6/01", // Confecção de roupas íntimas
                "1421-5/00", // Fabricação de meias
                "1529-7/00", // Fabricação de outros produtos de couro e peles
                "2542-0/00", // Fabricação de produtos de serralheria, exceto esquadrias
            ),
            SERVICOS to listOf(
                "9602-5/01", // Cabeleireiros, manicure e pedicure
                "9602-5/02", // Atividades de estética e outros serviços de cuidados com a beleza
                "8299-7/99", // Outras atividades de serviços prestados principalmente às empresas
                "9511-8/00", // Reparação e manutenção de computadores e equipamentos periféricos
                "7420-0/04", // Atividades de fotografia e similares
            ),
            MISTO to listOf(
                "5611-2/01", // Restaurantes e outros estabelecimentos de serviços de alimentação e bebidas
                "9603-3/01", // Gestão e manutenção de cemitérios
                "4520-0/01", // Serviços de manutenção e reparação mecânica de veículos automotores
                "9529-1/04", // Reparação de outros objetos pessoais e domésticos
                "4763-6/04", // Comércio varejista de artigos de caça, pesca e camping
            ),
        )
    }
}