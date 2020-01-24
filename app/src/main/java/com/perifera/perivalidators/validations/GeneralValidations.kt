
/**
 * Validaciones generales de la aplicación
 * @version 1.0
 * @author Cristian S
 * @date 2019/02/04
 */

package com.perifera.perivalidators.validations

import android.util.Log
import android.view.View

import com.perifera.perivalidators.validations.rules.BaseRule
import com.perifera.perivalidators.validations.rules.NonEmptyRule
import com.perifera.perivalidators.validations.rules.RegexMatch
import com.perifera.perivalidators.validations.rules.ValidCoin
import javax.xml.validation.Validator
import kotlin.math.log

class GeneralValidations(var text: String) {

    private var isValid = true
    private var errorMessage: String = ""

    var errorCallback: ((message: String) -> Unit)? = null
    var successCallback: ((data: Any) -> Unit)? = null
    var rulesList = ArrayList<BaseRule>()

    /**
     * Metodo para verificar que los campos no esten vacios
     * @author Cristian S
     * @date 2019/12/04
     * */
    fun nonEmpty(errorMsg:String? = null) : GeneralValidations
    {
        val rule = errorMsg?.let { NonEmptyRule(errorMsg) }?: NonEmptyRule()
        addRule(rule)
        return this
    }

    /**
     * Agrega el mensaje de error a la lista
     * @author Cristian S
     * @date 2019/12/05
     * @return error cuando los campos esten vacios
     * */
    fun addRule(rule: BaseRule) : GeneralValidations
    {
        rulesList.add(rule)
        return this
    }

    fun addErrorCallback(callback: (message: String) -> Unit) : GeneralValidations
    {
        errorCallback = callback
        return this
    }
    fun addSuccessCallback(callback: (data: Any) -> Unit) : GeneralValidations
    {

        isValid = true
        successCallback = callback
        return this
    }

    fun setError(message: String)
    {
        isValid = false
        errorMessage = message
    }


    fun check() : Boolean
    {
        for (rule in rulesList)
        {
            if (!rule.validate(text))
            {
                setError(rule.getErrorMessage())
                break
            }
        }



        // llamando los callbacks
        if (isValid) {
            if(text.startsWith("$")) {
                text = text.replace("$", "")
            }
            successCallback?.invoke(text)
        }
        else {
            errorCallback?.invoke(errorMessage)
        }

        return isValid
    }

    /**
     * Valida si el numero es valido y espera un Boolean
     * @author Cristian S
     * @date 2019/12/05
     * @return error cuando los campos esten vacios
     * */
    fun validNumber(simbol: String,errorMsg:String? = null) : GeneralValidations
    {
        val rule = errorMsg?.let { ValidCoin(simbol,errorMsg) }?: ValidCoin(simbol)
        addRule(rule)
        return this
    }


    fun regex(pattern: String, errorMsg:String? = null) : GeneralValidations
    {
        val rule = errorMsg?.let { RegexMatch(pattern,errorMsg) }?: RegexMatch(pattern)
        addRule(rule)
        return this
    }
    fun saveImage(){

    }
}