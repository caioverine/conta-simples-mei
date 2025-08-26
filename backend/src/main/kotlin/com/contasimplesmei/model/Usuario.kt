package com.contasimplesmei.model

import com.contasimplesmei.enums.PerfilUsuarioEnum
import com.contasimplesmei.enums.TipoNegocioEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.util.UUID

@Entity
@Table(name = "usuarios", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
data class Usuario(
    @Id
    @GeneratedValue
    val id: UUID? = null,
    @Column(nullable = false)
    val nome: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false, unique = true)
    val cnpj: String,
    @Column(nullable = false)
    val senha: String,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val perfil: PerfilUsuarioEnum,
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_negocio")
    val tipoNegocio: TipoNegocioEnum = TipoNegocioEnum.SERVICOS,
    @Column(name = "tem_funcionario")
    val temFuncionario: Boolean = false,
    @Column(name = "cnae_principal")
    val cnaePrincipal: String? = null,
)
