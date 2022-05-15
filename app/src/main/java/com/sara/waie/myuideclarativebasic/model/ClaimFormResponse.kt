package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class ClaimFormResponse(

    @SerializedName("Result") var Result: Boolean? = null,
    @SerializedName("Reason") var Reason: String? = null,
    @SerializedName("Claims") var Claims: ArrayList<Claim> = arrayListOf()

)