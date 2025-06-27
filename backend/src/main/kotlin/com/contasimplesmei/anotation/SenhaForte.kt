package com.contasimplesmei.anotation

import com.contasimplesmei.validator.SenhaForteValidator
import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [SenhaForteValidator::class])
annotation class SenhaForte(
    val message: String = "{validacao.senha.fraca}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
