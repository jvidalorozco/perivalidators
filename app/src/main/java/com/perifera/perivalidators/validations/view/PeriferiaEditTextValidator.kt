

package com.perifera.perivalidators.validations.view

import android.util.Log
import android.widget.EditText
import com.perifera.perivalidators.validations.GeneralValidations

import java.io.Console




    /**
     * Retorna el valor del EditText
     * @version 1.0
     * @author Cristian S
     * @date 2019/12/05
     */
    fun EditText.validator(): GeneralValidations {
        return GeneralValidations(text.toString())
    }

    /**
     * Verifica si el EditText esta vacio y espera un callback para asignar un mensaje de error
     * @version 1.0
     * @author Cristian S
     * @date 2019/12/05
     */
    fun EditText.nonEmpty(callback: (message: String) -> Unit): Boolean {
        return validator().nonEmpty()
                .addErrorCallback {
                    callback.invoke(it)
                }
                .check()
    }

    /**
     * Verifica si es valida la moneda
     * @version 1.0
     * @author Cristian S
     * @date 2019/12/05
     */
    fun EditText.validCoin(simbol: String, onFailure: (message: String) -> Unit, onSuccess: (data: Any) -> Unit): Boolean {
        return validator().validNumber(simbol)
                .addSuccessCallback {
                    onSuccess(it)
                }
                .addErrorCallback {
                    onFailure.invoke(it)
                }.check()
    }

