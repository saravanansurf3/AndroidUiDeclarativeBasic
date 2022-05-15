package com.sara.waie.myuideclarativebasic.utils

class ChoiceState(
    name: String,
    validators: List<Validators>,
    transform: Transform<String>? = null,
) : TextFieldState(name = name, validators = validators, transform = transform) {

    /**
     * This function all [validators] passed in to th state class against the state's value.
     *
     * @throws Exception if the used [Validators] class is not supported.
     * @return true if all validators pass, false otherwise.
     */
    override fun validate(): Boolean {
        val validations = validators.map {
            when (it) {
                is Validators.Required -> validateRequired(it.message)
                is Validators.Custom -> validateCustom(it.function, it.message)
                else -> throw Exception("${it::class.simpleName} validator cannot be applied to ChoiceState. Did you mean Validators.Custom?")
            }
        }
        return validations.all { it }
    }
}