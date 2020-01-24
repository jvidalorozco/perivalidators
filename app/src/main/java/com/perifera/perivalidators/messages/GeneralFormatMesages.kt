package com.perifera.perivalidators.messages

import android.content.Context
import android.widget.Toast

open class GeneralFormatMesages {

    fun toast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT){
        Toast.makeText(context,message,duration).show()
    }
}