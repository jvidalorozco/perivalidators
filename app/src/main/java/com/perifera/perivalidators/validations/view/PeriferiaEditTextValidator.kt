package com.perifera.perivalidators.validations.view

import android.content.Context
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.TextView


import com.perifera.perivalidators.validations.GeneralValidations

import java.io.Console
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.ParseException
import java.util.*


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
    fun EditText.validCoin(onFailure: (message: String) -> Unit, onSuccess: (data: Any) -> Unit): Boolean {
        return validator().validNumber(getSeparatorMiles(),getSymbol(this.context))
                .addSuccessCallback {
                    onSuccess(it)
                }
                .addErrorCallback {
                    onFailure.invoke(it)
                }.check()
    }


/**
 * Verifica si es valida la moneda
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/14
 * @param symbol simbolo de la moneda actual del dispositivo
 * @param pattern patron de formateo de moneda
 */
fun EditText.setMaskingMoney(symbol: String, pattern: String) {
    this.addTextChangedListener(object: CustomTextWatcher{
        val editTextWeakReference: WeakReference<EditText> = WeakReference(this@setMaskingMoney)
        override fun afterTextChanged(editable: Editable?) {
            val editText = editTextWeakReference.get() ?: return
            val s = editable.toString()
            editText.removeTextChangedListener(this)
            val cleanString = s.replace("["+getSymbol(context)+",.]".toRegex(), "")
            val newval = symbol + cleanString.monetize(context,pattern)

            editText.setText(newval)
            editText.setSelection(newval.length)
            editText.addTextChangedListener(this)
        }
    })
}


/**
 * Interfaz del textwatcher
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/14
 */
interface CustomTextWatcher: TextWatcher {
    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}


/**
 * aplicar el patron de la moneda
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/14
 */
fun String.monetize(context: Context, pattern: String): String{
    if (this.isEmpty() || this.equals(getSymbol(context))) {
        return ""
    }
    return DecimalFormat(pattern).format(this.replace("[^\\d]".toRegex(), "").toLong())
}


/**
 * Obtiene el simbolo de la moneda de la region
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/14
 */
fun getSymbol(context: Context): String {
    val currency = Currency.getInstance(getLocale(context))

    return currency.symbol
}

/**
 * Obtiene la configuracion local del dispositivo
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/14
 */
fun getLocale(ctx: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        ctx.resources.configuration.locales.get(0)
    } else {
        ctx.resources.configuration.locale
    }
}


/**
 * Obtiene el separador de miles
 * @version 1.0
 * @author Jorge V
 * @date 2020/02/15
 */
fun getSeparatorMiles(): String{
    return DecimalFormatSymbols.getInstance().decimalSeparator.toString()
}