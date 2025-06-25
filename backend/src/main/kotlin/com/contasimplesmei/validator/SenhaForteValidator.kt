package com.contasimplesmei.validator

import com.contasimplesmei.anotation.SenhaForte
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class SenhaForteValidator : ConstraintValidator<SenhaForte, String> {
    override fun isValid(senha: String?, context: ConstraintValidatorContext): Boolean {
        if (senha.isNullOrBlank()) return false

        val regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
        return senha.matches(regex)
    }
}