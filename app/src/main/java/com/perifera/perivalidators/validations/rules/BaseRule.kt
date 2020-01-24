
package com.perifera.perivalidators.validations.rules

interface BaseRule : Base {

    fun validate(text: String) : Boolean

}
interface BaseRuleWithData : BaseRule  {

    fun getSuccessData() : Any
    fun setSuccessData(any: Any)
}


interface Base {

    fun getErrorMessage() : String
    fun setError(msg: String)
}

interface BaseJson{

}
