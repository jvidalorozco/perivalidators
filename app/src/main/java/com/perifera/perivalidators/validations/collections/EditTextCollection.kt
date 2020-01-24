/**
 * Obtiene una lista de editText para validar que no esten vacios
 * @version 1.0
 * @author Cristian S
 * @date 2019/02/04
 */

package com.perifera.perivalidators.validations.collections

import android.widget.EditText
import com.perifera.perivalidators.validations.view.nonEmpty
import com.perifera.perivalidators.validations.view.validCoin


fun Any.nonEmptyList(vararg editTextList: EditText, callback: (view: EditText, message: String) -> Unit) : Boolean
{
    var result = false
    for (edittext in editTextList)
    {
        result = edittext.nonEmpty {
            callback.invoke(edittext, it)
        }

        if (result == false)
            return false
    }
    return result
}

/**
Validar el formato moneda
*/

fun Any.validMoney(simbol: String,vararg editTextList: EditText, onFailure: (view: EditText, message: String) -> Unit,onSuccess: (view: EditText, data: Any) -> Unit) : Boolean
{
    var result = false
    for (edittext in editTextList)
    {
        result = edittext.validCoin ( simbol,{
            onFailure.invoke(edittext, it)
        },{
            onSuccess.invoke(edittext,it)
        })

        if (result == false)
            return false
    }
    return result
}

