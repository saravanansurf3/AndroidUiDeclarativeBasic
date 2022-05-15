package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class Claimfield(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("required") var required: String? = null,
    @SerializedName("isdependant") var isdependant: String? = null,
    @SerializedName("created") var created: String? = null,
    @SerializedName("modified") var modified: String? = null,
    @SerializedName("Claimfieldoption") var Claimfieldoption: ArrayList<Claimfieldoption> = arrayListOf()

)