/**
* Verificar que los campos no esten vacios
* @version 1.0
* @author Cristian S
* @date 2019/12/05
*/

package com.perifera.perivalidators.validations.rules

class NonEmptyRule (var errorMsg: String  = "El campo no puede estar vacio") : BaseRule {

    override fun validate(text: String): Boolean = text.isNotEmpty()

    override fun getErrorMessage(): String = errorMsg

    override fun setError(msg: String){
        errorMsg = msg
    }

}