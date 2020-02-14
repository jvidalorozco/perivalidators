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
    fun EditText.validCoin(simbol: String, onFailure: (message: String) -> Unit, onSuccess: (data: Any) -> Unit): Boolean {
        return validator().validNumber(simbol)
                .addSuccessCallback {
                    onSuccess(it)
                }
                .addErrorCallback {
                    onFailure.invoke(it)
                }.check()
    }


fun TextView.currencyFormat() {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            removeTextChangedListener(this)
            text = if (s?.toString().isNullOrBlank()) {
                ""
            } else {
                s.toString().currencyFormat()
            }
            if(this@currencyFormat is EditText){
                setSelection(text.toString().length)
            }
            addTextChangedListener(this)
        }
    })
}

fun String.currencyFormat(): String {
    var current = this
    if (current.isEmpty()) current = "0"
    return try {
        if (current.contains('.')) {
            NumberFormat.getNumberInstance(Locale.getDefault()).format(current.replace(",", "").toDouble())
        } else {
            NumberFormat.getNumberInstance(Locale.getDefault()).format(current.replace(",", "").toLong())
        }
    } catch (e: Exception) {
        "0"
    }
}



    fun formatMoney(context: Context, text: String) : String{

        val mNumberFormat  = NumberFormat.getNumberInstance(getLocale(context))
        val mCurrencyFormat = NumberFormat.getCurrencyInstance(getLocale(context))
        var myFormattedPrice : String = ""



                try {

                    // Use the number format for the locale.
                    var mInputQuantity = mNumberFormat.parse(text).toDouble()

                    // TODO: Set up the price and currency format.

                    /*var deviceLocale = Locale.getDefault().country
                    // If country code is France or Israel, calculate price
                    // with exchange rate and change to the country's currency format.
                    if (deviceLocale.equals("FR") || deviceLocale.equals("IL")) {
                        if (deviceLocale.equals("FR")) {
                            // Calculate mPrice in euros.
                            mPrice *= mFrExchangeRate
                        } else {
                            // Calculate mPrice in new shekels.
                            mPrice *= mIwExchangeRate;
                        }
                        // Use the user-chosen locale's currency format, which
                        // is either France or Israel.
                        myFormattedPrice = mCurrencyFormat.format(mInputQuantity)
                    } else {
                        // mPrice is the same (based on U.S. dollar).
                        // Use the currency format for the U.S.
                        mCurrencyFormat = NumberFormat.getCurrencyInstance(getLocale())
                        myFormattedPrice = mCurrencyFormat.format(mInputQuantity)
                    }*/

                    myFormattedPrice = mCurrencyFormat.format(mInputQuantity)


                    return myFormattedPrice

                } catch (e: ParseException) {
                    e.printStackTrace()

                }

        return text
    }

fun EditText.onTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            afterTextChanged.invoke(formatMoney(context,p0.toString().replace(getSymbol(context),"")))
        }

        override fun afterTextChanged(editable: Editable?) {

        }
    })
}

fun getSymbol(context: Context): String {
    val currency = Currency.getInstance(getLocale(context))

    return currency.symbol
}

fun getLocale(ctx: Context): Locale {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        ctx.resources.configuration.locales.get(0)
    } else {
        ctx.resources.configuration.locale
    }
}
