package com.perifera.perivalidators.validations.rules

class RegexMatch(val pattern: String, var errorMsg: String = "No es un formato de moneda valido") : BaseRule {

    override fun validate(text: String): Boolean {
        return text.matches(Regex(pattern))
    }

    override fun getErrorMessage(): String = errorMsg

    override fun setError(msg: String) {
        errorMsg = msg
    }
}