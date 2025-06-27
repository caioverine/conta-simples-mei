package com.contasimplesmei.service

import com.contasimplesmei.dto.CategoriaRequestDTO
import com.contasimplesmei.dto.CategoriaResponseDTO
import com.contasimplesmei.exception.BusinessException
import com.contasimplesmei.mapper.toResponseDTO
import com.contasimplesmei.model.Categoria
import com.contasimplesmei.model.Usuario
import com.contasimplesmei.repository.CategoriaRepository
import com.contasimplesmei.security.UsuarioAutenticadoProvider
import jakarta.persistence.EntityNotFoundException
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class CategoriaService(
    private val messageSource: MessageSource,
    private val usuarioAutenticadoProvider: UsuarioAutenticadoProvider,
    private val repository: CategoriaRepository,
) {
    fun listar(): List<CategoriaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        return repository.findAllByAtivoTrueAndUsuarioId(usuarioLogado.id!!).map { it.toResponseDTO() }
    }

    fun listarPorTipo(tipo: String): List<CategoriaResponseDTO> {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val tipoCategoria =
            runCatching {
                tipo.uppercase()
            }.getOrElse {
                throw IllegalArgumentException(
                    messageSource.getMessage(
                        "tipo.categoria.invalido",
                        null,
                        LocaleContextHolder.getLocale(),
                    ),
                )
            }

        return repository
            .findAllByTipoAndAtivoTrueAndUsuarioId(
                tipoCategoria,
                usuarioLogado.id!!,
            ).map { it.toResponseDTO() }
    }

    @Transactional
    fun criar(dto: CategoriaRequestDTO): CategoriaResponseDTO {
        // TODO Validar duplicidade de nome de categoria com BusinessException
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()
        val categoria = Categoria(nome = dto.nome, tipo = dto.tipo, usuario = usuarioLogado)
        return repository.save(categoria).toResponseDTO()
    }

    @Transactional
    fun deletar(id: UUID) {
        val usuarioLogado = usuarioAutenticadoProvider.getUsuarioLogado()

        val categoria =
            repository
                .findById(id)
                .orElseThrow {
                    EntityNotFoundException(
                        messageSource.getMessage(
                            "categoria.nao.encontrada",
                            null,
                            LocaleContextHolder.getLocale(),
                        ),
                    )
                }

        if (categoria.usuario.id != usuarioLogado.id) {
            throw BusinessException(
                messageSource.getMessage(
                    "categoria.nao.pertence.usuario.logado",
                    null,
                    LocaleContextHolder.getLocale(),
                ),
            )
        }

        if (!categoria.ativo) {
            throw BusinessException(
                messageSource.getMessage(
                    "categoria.inativa",
                    null,
                    LocaleContextHolder.getLocale(),
                ),
            )
        }

        val categoriaInativa = categoria.copy(ativo = false)
        repository.save(categoriaInativa)
    }

    fun buscarPorId(id: UUID): CategoriaResponseDTO =
        repository.findById(id).orElseThrow { EntityNotFoundException("Categoria não encontrada") }.toResponseDTO()

    @Transactional
    fun criarCategoriasPadraoParaUsuario(usuario: Usuario) {
        val categorias: MutableList<Categoria> = mutableListOf()
        preencherCategoriasReceitaPadraoParaUsuario(categorias, usuario)
        preencherCategoriasDespesaPadraoParaUsuario(categorias, usuario)
        repository.saveAll(categorias)
    }

    private fun preencherCategoriasReceitaPadraoParaUsuario(
        categorias: MutableList<Categoria>,
        usuario: Usuario,
    ) {
        categorias.addAll(
            listOf(
                Categoria(nome = "Venda de produtos", tipo = "RECEITA", usuario = usuario),
                Categoria(nome = "Prestação de serviços", tipo = "RECEITA", usuario = usuario),
                Categoria(nome = "Rendimentos financeiros", tipo = "RECEITA", usuario = usuario),
                Categoria(nome = "Outros recebimentos", tipo = "RECEITA", usuario = usuario),
            ),
        )
    }

    private fun preencherCategoriasDespesaPadraoParaUsuario(
        categorias: MutableList<Categoria>,
        usuario: Usuario,
    ) {
        categorias.addAll(
            listOf(
                Categoria(nome = "Aluguel e infraestrutura", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Materiais e insumos", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Transporte e logística", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Contas e utilidades", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Marketing e divulgação", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Impostos e taxas", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Despesas bancárias", tipo = "DESPESA", usuario = usuario),
                Categoria(nome = "Outras despesas", tipo = "DESPESA", usuario = usuario),
            ),
        )
    }
}
