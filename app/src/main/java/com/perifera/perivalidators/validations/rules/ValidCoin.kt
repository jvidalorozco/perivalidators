package com.perifera.perivalidators.validations.rules


import android.os.Build
import android.util.Log
import com.perifera.perivalidators.validations.GeneralValidations


import java.util.*
import javax.xml.validation.Validator

class ValidCoin (val separator: String,val simbol : String,var errorMsg: String = "Moneda Invalida", var data: Any = "") : BaseRuleWithData{


    override fun getSuccessData(): Any = data

    override fun setSuccessData(_data: Any) {
       data  = _data
    }

    override fun validate(text: String): Boolean {
        var res =  text
        if (res.isEmpty())
            return false

        /**
         * Verificar si la cadena tiene caracteres especiales
         */

        if (text.contains(simbol)) {
            res = text.replace(simbol, "")
        }

        return GeneralValidations(res).regex("^[0-9]+([${separator}][0-9]+)?\$").check()
    }

    override fun getErrorMessage(): String = errorMsg

    override fun setError(msg: String) {
        errorMsg = msg
    }
}

