package com.sara.waie.myuideclarativebasic.utils

import kotlin.reflect.KClass
import kotlin.reflect.KParameter

open class FormState<T : BaseState<*>>(val fields: HashMap<String,T>) {

    /**
     * This function is used to validate the whole form. It goes through all fields calling the [BaseState.validate] function. If all of them return true, then the function also returns true.
     */
    fun validate(): Boolean = fields.map { it.value.validate() }.all { it }

    /**
     * This function gets a single field state. It uses the name specified in the [BaseState.name] field to find the field.
     * @param name the name of the field to get.
     */
    inline fun <reified u> getState(name: String): u = fields.getValue(name) as u

    /**
     * This function is used to access the data in the whole form. It goes through all fields calling the [BaseState.getData] function and stores them in a [Map] of [String] to [Any]. This map is then used in a constructor to create the specified class.
     * @param dataClass the class to create using the data in the form data.
     */
    fun <T : Any> getData(dataClass: KClass<T>): T {
        val map = fields.values.associate { it.name to it.getData() }
        val constructor = dataClass.constructors.last()
        val args: Map<KParameter, Any?> = constructor.parameters.associateWith { map[it.name] }
        return constructor.callBy(args)
    }

    fun  getData(): Map<String, Any?> {


        return fields.values.associate { it.name to it.getData() }
    }

    fun cleanForm(){
        fields.clear()
    }
    fun addField(elementId:String,newField:T){
        fields.put(elementId,newField)
    }
}