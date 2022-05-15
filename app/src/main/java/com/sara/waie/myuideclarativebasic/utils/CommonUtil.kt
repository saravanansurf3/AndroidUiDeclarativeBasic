package com.sara.waie.myuideclarativebasic.utils

fun String.isNumeric(): Boolean {
    return this.toIntOrNull()?.let { true } ?: false
}