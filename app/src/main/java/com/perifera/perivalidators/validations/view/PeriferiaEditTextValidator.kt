

package com.perifera.perivalidators.validations.view

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.perifera.perivalidators.utils.Util

import com.perifera.perivalidators.validations.GeneralValidations

import java.io.Console
import java.text.NumberFormat
import java.text.ParseException


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



    fun EditText.formatMoney(){
        val util = Util()
        val mNumberFormat  = NumberFormat.getNumberInstance(util.getLocale(context))
        val mCurrencyFormat = NumberFormat.getCurrencyInstance(util.getLocale(context))
        var myFormattedPrice : String = ""

        this.addTextChangedListener(object: TextWatcher {override fun afterTextChanged(s: Editable?) {

        }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                try {

                    // Use the number format for the locale.
                    var mInputQuantity = mNumberFormat.parse(s?.toString()).toInt()

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

                    setText(myFormattedPrice)

                } catch ( e: ParseException) {
                    e.printStackTrace()

                }
            }

        })



    }

