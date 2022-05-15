package com.sara.waie.myuideclarativebasic.model

import com.google.gson.annotations.SerializedName


data class Claimfieldoption(

    @SerializedName("name") var name: String? = null,
    @SerializedName("label") var label: String? = null,
    @SerializedName("belongsto") var belongsto: String? = null,
    @SerializedName("hasmany") var hasmany: String? = null,
    @SerializedName("claimfield_id") var claimfieldId: String? = null,
    @SerializedName("id") var id: String? = null

)